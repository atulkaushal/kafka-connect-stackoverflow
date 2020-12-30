package com.github.atulkaushal.kafka.connect.stackoverflow;

import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.BATCH_SIZE_CONFIG;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.SINCE_CONFIG;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.TAGS_CONFIG;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.TOPIC_CONFIG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.github.atulkaushal.kafka.connect.stackoverflow.model.Question;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * The Class StackOverFlowSourceTaskTest.
 *
 * @author Atul
 */
public class StackOverFlowSourceTaskTest {

  /** The stack over flow source task. */
  private StackOverFlowSourceTask stackOverFlowSourceTask = new StackOverFlowSourceTask();

  /** The batch size. */
  private Integer batchSize = 10;

  /**
   * Initial config.
   *
   * @return the map
   */
  private Map<String, String> initialConfig() {
    Map<String, String> baseProps = new HashMap<>();
    baseProps.put(TAGS_CONFIG, "java");
    baseProps.put(SINCE_CONFIG, Instant.now().toString());
    baseProps.put(BATCH_SIZE_CONFIG, batchSize.toString());
    baseProps.put(TOPIC_CONFIG, "stackoverflow-questions");
    return baseProps;
  }

  /**
   * Test.
   *
   * @throws UnirestException the unirest exception
   */
  @Test
  public void test() throws UnirestException {
    stackOverFlowSourceTask.config = new StackOverFlowSourceConnectorConfig(initialConfig());
    stackOverFlowSourceTask.nextPageToVisit = 1;
    stackOverFlowSourceTask.nextQuerySince =
        LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
    stackOverFlowSourceTask.stackOverFlowHttpAPIClient =
        new StackOverFlowAPIHttpClient(stackOverFlowSourceTask.config);
    String url =
        stackOverFlowSourceTask.stackOverFlowHttpAPIClient.constructUrl(
            stackOverFlowSourceTask.nextQuerySince);
    System.out.println(url);
    HttpResponse<JsonNode> httpResponse =
        stackOverFlowSourceTask.stackOverFlowHttpAPIClient.getNextQuestionsAPI(
            stackOverFlowSourceTask.nextQuerySince);
    if (httpResponse.getStatus() != 403) {
      assertEquals(200, httpResponse.getStatus());
      // Set<String> headers = httpResponse.getHeaders().keySet();
      // assertTrue(headers.contains(X_RATELIMIT_LIMIT_HEADER));
      // assertTrue(headers.contains(X_RATELIMIT_REMAINING_HEADER));
      // assertTrue(headers.contains(X_RATELIMIT_RESET_HEADER));
      assertTrue(
          batchSize.intValue()
              >= httpResponse.getBody().getObject().getJSONArray("items").length());
      JSONObject jsonObject =
          (JSONObject) httpResponse.getBody().getObject().getJSONArray("items").get(0);
      System.out.println(jsonObject.toString());
      Question question = Question.fromJson(jsonObject);
      assertNotNull(question);
      assertNotNull(question.getQuestionId());
      /*// Date todaysDate = Date.(LocalDate.now().atStartOfDay());
      System.out.println(LocalDate.now().atStartOfDay());
      System.out.println(Date.from(question.getCreationDate()));
      assertEquals(LocalDate.now().atStartOfDay(), Date.from(question.getCreationDate()));*/
    }
  }
}
