package com.github.atulkaushal.kafka.connect.stackoverflow;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.apache.kafka.connect.errors.ConnectException;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

/**
 * The Class StackOverFlowAPIHttpClient.
 *
 * @author Atul
 */
public class StackOverFlowAPIHttpClient {

  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(StackOverFlowAPIHttpClient.class);

  /** The X rate limit. */
  // for efficient http requests
  private Integer xRateLimit = 9999;

  /** The X rate remaining. */
  private Integer xRateRemaining = 9999;

  /** The X rate reset. */
  private long xRateReset = Instant.MAX.getEpochSecond();

  /** The config. */
  StackOverFlowSourceConnectorConfig config;

  /** The Constant X_RATELIMIT_LIMIT_HEADER. */
  public static final String X_RATELIMIT_LIMIT_HEADER = "X-Ratelimit-Limit";

  /** The Constant X_RATELIMIT_REMAINING_HEADER. */
  public static final String X_RATELIMIT_REMAINING_HEADER = "X-Ratelimit-Remaining";

  /** The Constant X_RATELIMIT_RESET_HEADER. */
  public static final String X_RATELIMIT_RESET_HEADER = "X-Ratelimit-Reset";

  /**
   * Instantiates a new stack over flow API http client.
   *
   * @param config the config
   */
  public StackOverFlowAPIHttpClient(StackOverFlowSourceConnectorConfig config) {
    this.config = config;
  }

  /**
   * Gets the next questions.
   *
   * @param page the page
   * @param since the since
   * @return the next questions
   * @throws InterruptedException the interrupted exception
   */
  protected JSONArray getNextQuestions(Integer page, Instant since) throws InterruptedException {

    HttpResponse<JsonNode> jsonResponse;
    try {
      jsonResponse = getNextQuestionsAPI(page, since);

      // deal with headers in any case
      Headers headers = jsonResponse.getHeaders();
      xRateLimit = Integer.valueOf(headers.getFirst(X_RATELIMIT_LIMIT_HEADER));
      xRateRemaining = Integer.valueOf(headers.getFirst(X_RATELIMIT_REMAINING_HEADER));
      xRateReset = Integer.valueOf(headers.getFirst(X_RATELIMIT_RESET_HEADER));
      switch (jsonResponse.getStatus()) {
        case 200:
          return jsonResponse.getBody().getArray();
        case 401:
          throw new ConnectException("Bad GitHub credentials provided, please edit your config");
        case 403:
          // we have issues too many requests.
          log.info(jsonResponse.getBody().getObject().getString("message"));
          log.info(String.format("Your rate limit is %s", xRateLimit));
          log.info(String.format("Your remaining calls is %s", xRateRemaining));
          log.info(
              String.format(
                  "The limit will reset at %s",
                  LocalDateTime.ofInstant(
                      Instant.ofEpochSecond(xRateReset), ZoneOffset.systemDefault())));
          long sleepTime = xRateReset - Instant.now().getEpochSecond();
          log.info(String.format("Sleeping for %s seconds", sleepTime));
          Thread.sleep(1000 * sleepTime);
          return getNextQuestions(page, since);
        default:
          log.error(constructUrl(page, since));
          log.error(String.valueOf(jsonResponse.getStatus()));
          log.error(jsonResponse.getBody().toString());
          log.error(jsonResponse.getHeaders().toString());
          log.error("Unknown error: Sleeping 5 seconds " + "before re-trying");
          Thread.sleep(5000L);
          return getNextQuestions(page, since);
      }
    } catch (UnirestException e) {
      e.printStackTrace();
      Thread.sleep(5000L);
      return new JSONArray();
    }
  }

  /**
   * Gets the next questions API.
   *
   * @param page the page
   * @param since the since
   * @return the next questions API
   * @throws UnirestException the unirest exception
   */
  protected HttpResponse<JsonNode> getNextQuestionsAPI(Integer page, Instant since)
      throws UnirestException {
    GetRequest unirest = Unirest.get(constructUrl(page, since));
    if (!config.getAuthUsername().isEmpty() && !config.getAuthPassword().isEmpty()) {
      unirest = unirest.basicAuth(config.getAuthUsername(), config.getAuthPassword());
    }
    log.debug(String.format("GET %s", unirest.getUrl()));
    return unirest.asJson();
  }

  /**
   * Construct url.
   *
   * @param page the page
   * @param since the since
   * @return the string
   */
  protected String constructUrl(Integer page, Instant since) {
    return String.format(
        "https://api.stackexchange.com/2.2/questions?pagesize=2&todate=1608508800&order=desc&sort=creation&tagged=%s&site=stackoverflow" // ,
        // config.getTags(), config.getRepoConfig(), page, config.getBatchSize(), since.toString()
        );
  }

  /**
   * Sleep.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void sleep() throws InterruptedException {
    long sleepTime =
        (long) Math.ceil((double) (xRateReset - Instant.now().getEpochSecond()) / xRateRemaining);
    log.debug(String.format("Sleeping for %s seconds", sleepTime));
    Thread.sleep(1000 * sleepTime);
  }

  /**
   * Sleep if need.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void sleepIfNeed() throws InterruptedException {
    // Sleep if needed
    if (xRateRemaining <= 10 && xRateRemaining > 0) {
      log.info(String.format("Approaching limit soon, you have %s requests left", xRateRemaining));
      sleep();
    }
  }
}
