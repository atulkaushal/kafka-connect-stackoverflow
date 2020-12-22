package com.github.atulkaushal.kafka.connect.stackoverflow;

import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.CREATION_DATE_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.DISPLAY_NAME_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.IS_ANSWERED_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.KEY_SCHEMA;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.LAST_ACTIVITY_DATE_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.LINK_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.NEXT_PAGE_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.OWNER_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.OWNER_LINK_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.OWNER_SCHEMA;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.QUESTION_ID_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.TAGS_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.TITLE_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.USER_ID_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.USER_TYPE_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.VALUE_SCHEMA;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.atulkaushal.kafka.connect.stackoverflow.model.Owner;
import com.github.atulkaushal.kafka.connect.stackoverflow.model.Question;
import com.github.atulkaushal.kafka.connect.stackoverflow.utils.DateUtils;
import com.github.atulkaushal.kafka.connect.stackoverflow.utils.VersionUtil;

/**
 * The Class StackOverFlowSourceTask.
 *
 * @author Atul
 */
public class StackOverFlowSourceTask extends SourceTask {

  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(StackOverFlowSourceTask.class);

  /** The config. */
  public StackOverFlowSourceConnectorConfig config;

  /** The next query since. */
  protected Instant nextQuerySince;

  /** The last question number. */
  protected Integer lastQuestionNumber;

  /** The next page to visit. */
  protected Integer nextPageToVisit = 1;

  /** The last updated at. */
  protected Instant lastUpdatedAt;

  /** The stack over flow http API client. */
  StackOverFlowAPIHttpClient stackOverFlowHttpAPIClient;

  /**
   * Version.
   *
   * @return the string
   */
  @Override
  public String version() {
    return VersionUtil.getVersion();
  }

  /**
   * Start.
   *
   * @param map the map
   */
  @Override
  public void start(Map<String, String> map) {
    // Do things here that are required to start your task. This could be open a connection to a
    // database, etc.
    config = new StackOverFlowSourceConnectorConfig(map);
    initializeLastVariables();
    stackOverFlowHttpAPIClient = new StackOverFlowAPIHttpClient(config);
  }

  /** Initialize last variables. */
  private void initializeLastVariables() {
    Map<String, Object> lastSourceOffset = null;
    lastSourceOffset = context.offsetStorageReader().offset(sourcePartition());
    if (lastSourceOffset == null) {
      // we haven't fetched anything yet, so we initialize to 7 days ago
      nextQuerySince = config.getSince();
      lastQuestionNumber = -1;
    } else {
      Object updatedAt = lastSourceOffset.get(LAST_ACTIVITY_DATE_FIELD);
      Object questionNumber = lastSourceOffset.get(QUESTION_ID_FIELD);
      Object nextPage = lastSourceOffset.get(NEXT_PAGE_FIELD);
      if (updatedAt != null && (updatedAt instanceof String)) {
        nextQuerySince = Instant.parse((String) updatedAt);
      }
      if (questionNumber != null && (questionNumber instanceof String)) {
        lastQuestionNumber = Integer.valueOf((String) questionNumber);
      }
      if (nextPage != null && (nextPage instanceof String)) {
        nextPageToVisit = Integer.valueOf((String) nextPage);
      }
    }
  }

  /**
   * Poll.
   *
   * @return the list
   * @throws InterruptedException the interrupted exception
   */
  @Override
  public List<SourceRecord> poll() throws InterruptedException {
    stackOverFlowHttpAPIClient.sleepIfNeed();

    // fetch data
    final ArrayList<SourceRecord> records = new ArrayList<>();
    JSONArray questions =
        stackOverFlowHttpAPIClient.getNextQuestions(nextPageToVisit, nextQuerySince);
    // we'll count how many results we get with i
    int i = 0;
    for (Object obj : questions) {
      Question question = Question.fromJson((JSONObject) obj);
      SourceRecord sourceRecord = generateSourceRecord(question);
      records.add(sourceRecord);
      i += 1;
      lastUpdatedAt = question.getLastActivityDate();
    }
    if (i > 0) log.info(String.format("Fetched %s record(s)", i));
    if (i == 100) {
      // we have reached a full batch, we need to get the next one
      nextPageToVisit += 1;
    } else {
      nextQuerySince = lastUpdatedAt.plusSeconds(1);
      nextPageToVisit = 1;
      stackOverFlowHttpAPIClient.sleep();
    }
    return records;
  }

  /**
   * Generate source record.
   *
   * @param question the question
   * @return the source record
   */
  private SourceRecord generateSourceRecord(Question question) {
    return new SourceRecord(
        sourcePartition(),
        sourceOffset(question.getLastActivityDate()),
        config.getTopic(),
        null, // partition will be inferred by the framework
        KEY_SCHEMA,
        buildRecordKey(question),
        VALUE_SCHEMA,
        buildRecordValue(question),
        question.getLastActivityDate().toEpochMilli());
  }

  /** Stop. */
  @Override
  public void stop() {
    // Do whatever is required to stop your task.
  }

  /**
   * Source partition.
   *
   * @return the map
   */
  private Map<String, String> sourcePartition() {
    Map<String, String> map = new HashMap<>();
    map.put(QUESTION_ID_FIELD, config.getTags());
    return map;
  }

  /**
   * Source offset.
   *
   * @param updatedAt the updated at
   * @return the map
   */
  private Map<String, String> sourceOffset(Instant updatedAt) {
    Map<String, String> map = new HashMap<>();
    map.put(LAST_ACTIVITY_DATE_FIELD, DateUtils.maxInstant(updatedAt, nextQuerySince).toString());
    map.put(NEXT_PAGE_FIELD, nextPageToVisit.toString());
    return map;
  }

  /**
   * Builds the record key.
   *
   * @param question the question
   * @return the struct
   */
  private Struct buildRecordKey(Question question) {
    // Key Schema
    Struct key =
        new Struct(KEY_SCHEMA)
            .put(QUESTION_ID_FIELD, question.getQuestionId())
            .put(LAST_ACTIVITY_DATE_FIELD, question.getLastActivityDate());

    return key;
  }

  /**
   * Builds the record value.
   *
   * @param question the question
   * @return the struct
   */
  public Struct buildRecordValue(Question question) {

    // Question top level fields
    Struct valueStruct =
        new Struct(VALUE_SCHEMA)
            .put(IS_ANSWERED_FIELD, question.isAnswered())
            .put(TITLE_FIELD, question.getTitle())
            .put(LAST_ACTIVITY_DATE_FIELD, Date.from(question.getLastActivityDate()))
            .put(CREATION_DATE_FIELD, Date.from(question.getCreationDate()))
            .put(QUESTION_ID_FIELD, question.getQuestionId())
            .put(LINK_FIELD, question.getLinkToQuestion())
            .put(TAGS_FIELD, question.getTags());

    // Owner is mandatory
    Owner owner = question.getOwner();
    Struct ownerStruct =
        new Struct(OWNER_SCHEMA)
            .put(OWNER_LINK_FIELD, owner.getProfileLink())
            .put(USER_ID_FIELD, owner.getUserId())
            .put(DISPLAY_NAME_FIELD, owner.getDisplayName())
            .put(USER_TYPE_FIELD, owner.getUserType());
    valueStruct.put(OWNER_FIELD, ownerStruct);

    return valueStruct;
  }
}
