package com.github.atulkaushal.kafka.connect.stackoverflow;

import java.time.Instant;

import org.apache.kafka.connect.errors.ConnectException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  // private Integer xRateLimit = 9999;

  /** The X rate remaining. */
  // private Integer xRateRemaining = 9999;

  /** The X rate reset. */
  // private long xRateReset = Instant.MAX.getEpochSecond();

  /** The config. */
  StackOverFlowSourceConnectorConfig config;

  /** The Constant X_RATELIMIT_LIMIT_HEADER. */
  // public static final String X_RATELIMIT_LIMIT_HEADER = "X-Ratelimit-Limit";

  /** The Constant X_RATELIMIT_REMAINING_HEADER. */
  // public static final String X_RATELIMIT_REMAINING_HEADER = "X-Ratelimit-Remaining";

  /** The Constant X_RATELIMIT_RESET_HEADER. */
  // public static final String X_RATELIMIT_RESET_HEADER = "X-Ratelimit-Reset";

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
  protected JSONObject getNextQuestions(Instant since) throws InterruptedException {

    HttpResponse<JsonNode> jsonResponse;
    try {
      jsonResponse = getNextQuestionsAPI(since);

      // deal with headers in any case
      // Headers headers = jsonResponse.getHeaders();
      // xRateLimit = Integer.valueOf(headers.getFirst(X_RATELIMIT_LIMIT_HEADER));
      // xRateRemaining = Integer.valueOf(headers.getFirst(X_RATELIMIT_REMAINING_HEADER));
      // xRateReset = Integer.valueOf(headers.getFirst(X_RATELIMIT_RESET_HEADER));
      log.info("********************************************************");
      log.info(jsonResponse.toString());
      log.info("********************************************************");
      switch (jsonResponse.getStatus()) {
        case 200:
          return jsonResponse.getBody().getObject();
        case 401:
          throw new ConnectException(
              "Bad Stackoverflow credentials provided, please edit your config");
        case 403:
          // we have issues too many requests.
          log.info(jsonResponse.getBody().getObject().toString());
          /*log.info(String.format("Your rate limit is %s", xRateLimit));
          log.info(String.format("Your remaining calls is %s", xRateRemaining));
          log.info(
          String.format(
              "The limit will reset at %s",
              LocalDateTime.ofInstant(
                  Instant.ofEpochSecond(xRateReset), ZoneOffset.systemDefault())));*/
          long sleepTime = 5L; // xRateReset - Instant.now().getEpochSecond();
          log.info(String.format("Sleeping for %s seconds", sleepTime));
          Thread.sleep(1000 * sleepTime);
          return getNextQuestions(since);
        default:
          log.error(constructUrl(since));
          log.error(String.valueOf(jsonResponse.getStatus()));
          log.error(jsonResponse.getBody().toString());
          log.error(jsonResponse.getHeaders().toString());
          log.error("Unknown error: Sleeping 5 seconds " + "before re-trying");
          Thread.sleep(5000L);
          return getNextQuestions(since);
      }
    } catch (UnirestException e) {
      e.printStackTrace();
      Thread.sleep(5000L);
      return new JSONObject();
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
  protected HttpResponse<JsonNode> getNextQuestionsAPI(Instant since) throws UnirestException {
    GetRequest unirest = Unirest.get(constructUrl(since));
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
  protected String constructUrl(Instant since) {
    return String.format(
        "https://api.stackexchange.com/2.2/questions?pagesize=%s&fromdate=%s&order=asc&sort=creation&tagged=%s&site=stackoverflow",
        config.getBatchSize(), since.getEpochSecond(), config.getTags());
  }

  /**
   * Sleep.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void sleep() throws InterruptedException {
    long sleepTime = 5L;
    // (long) Math.ceil((double) (xRateReset - Instant.now().getEpochSecond()) / xRateRemaining);
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
    /* if (xRateRemaining <= 10 && xRateRemaining > 0) {
      log.info(String.format("Approaching limit soon, you have %s requests left", xRateRemaining));
      sleep();
    }*/
    sleep();
  }
}
