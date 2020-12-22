package com.github.atulkaushal.kafka.connect.stackoverflow.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

/**
 * The Class QuestionTest.
 *
 * @author Atul
 */
public class QuestionTest {

  /** The question str. */
  String questionStr =
      "{\r\n"
          + "      \"tags\": [\r\n"
          + "        \"java\",\r\n"
          + "        \"maven\",\r\n"
          + "        \"apache-kafka\",\r\n"
          + "        \"apache-storm\"\r\n"
          + "      ],\r\n"
          + "      \"owner\": {\r\n"
          + "        \"reputation\": 21,\r\n"
          + "        \"user_id\": 14713349,\r\n"
          + "        \"user_type\": \"registered\",\r\n"
          + "        \"profile_image\": \"https://www.gravatar.com/avatar/9fe5da50041467f211c66f553d644991?s=128&d=identicon&r=PG&f=1\",\r\n"
          + "        \"display_name\": \"BlueNebulae\",\r\n"
          + "        \"link\": \"https://stackoverflow.com/users/14713349/bluenebulae\"\r\n"
          + "      },\r\n"
          + "      \"is_answered\": false,\r\n"
          + "      \"view_count\": 22,\r\n"
          + "      \"answer_count\": 0,\r\n"
          + "      \"score\": 0,\r\n"
          + "      \"last_activity_date\": 1608489963,\r\n"
          + "      \"creation_date\": 1608489963,\r\n"
          + "      \"question_id\": 65383547,\r\n"
          + "      \"content_license\": \"CC BY-SA 4.0\",\r\n"
          + "      \"link\": \"https://stackoverflow.com/questions/65383547/why-is-a-java-lang-noclassdeffounderror-raised-org-apache-storm-kafka-spout-kaf\",\r\n"
          + "      \"title\": \"Why is a java.lang.NoClassDefFoundError raised (org/apache/storm/kafka/spout/KafkaSpout) when jar is run with Apache Storm?\"\r\n"
          + "    }";

  /** The question json. */
  private JSONObject questionJson = new JSONObject(questionStr);

  /** Can parse json. */
  @Test
  public void canParseJson() {
    Question question = Question.fromJson(questionJson);
    assertEquals(
        question.getLinkToQuestion(),
        "https://stackoverflow.com/questions/65383547/why-is-a-java-lang-noclassdeffounderror-raised-org-apache-storm-kafka-spout-kaf");
    assertEquals(
        question.getTitle(),
        "Why is a java.lang.NoClassDefFoundError raised (org/apache/storm/kafka/spout/KafkaSpout) when jar is run with Apache Storm?");
  }
}
