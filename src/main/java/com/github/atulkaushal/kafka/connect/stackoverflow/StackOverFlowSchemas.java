package com.github.atulkaushal.kafka.connect.stackoverflow;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Timestamp;

/**
 * The Class StackOverFlowSchemas.
 *
 * @author Atul
 */
public class StackOverFlowSchemas {

  public static final String NEXT_PAGE_FIELD = "next_page";
  // Question Fields
  /** The Constant TAGS_FIELD. */
  public static final String TAGS_FIELD = "tags";

  /** The Constant IS_ANSWERED_FIELD. */
  public static final String IS_ANSWERED_FIELD = "is_answered";

  /** The Constant VIEW_COUNT_FIELD. */
  public static final String VIEW_COUNT_FIELD = "view_count";

  /** The Constant ANSWER_COUNT_FIELD. */
  public static final String ANSWER_COUNT_FIELD = "answer_count";

  /** The Constant SCORE_FIELD. */
  public static final String SCORE_FIELD = "score";

  /** The Constant LAST_ACTIVITY_DATE_FIELD. */
  public static final String LAST_ACTIVITY_DATE_FIELD = "last_activity_date";

  /** The Constant CREATION_DATE_FIELD. */
  public static final String CREATION_DATE_FIELD = "creation_date";

  /** The Constant QUESTION_ID_FIELD. */
  public static final String QUESTION_ID_FIELD = "question_id";

  /** The Constant CONTENT_LICENSE_FIELD. */
  public static final String CONTENT_LICENSE_FIELD = "content_license";

  /** The Constant LINK_FIELD. */
  public static final String LINK_FIELD = "link";

  /** The Constant TITLE_FIELD. */
  public static final String TITLE_FIELD = "title";

  // Owner Fields
  /** The Constant OWNER_FIELD. */
  public static final String OWNER_FIELD = "owner";

  /** The Constant REPUTATION_FIELD. */
  public static final String REPUTATION_FIELD = "reputation";

  /** The Constant USER_ID_FIELD. */
  public static final String USER_ID_FIELD = "user_id";

  /** The Constant USER_TYPE_FIELD. */
  public static final String USER_TYPE_FIELD = "user_type";

  /** The Constant PROFILE_IMAGE_FIELD. */
  public static final String PROFILE_IMAGE_FIELD = "profile_image";

  /** The Constant DISPLAY_NAME_FIELD. */
  public static final String DISPLAY_NAME_FIELD = "display_name";

  /** The Constant OWNER_LINK_FIELD. */
  public static final String OWNER_LINK_FIELD = "link";

  /** The Constant SCHEMA_KEY. */
  // Schema names
  public static final String SCHEMA_KEY = "com.atulkaushal.kafka.connect.github.QuestionKey";

  /** The Constant SCHEMA_VALUE_QUESTION. */
  public static final String SCHEMA_VALUE_QUESTION =
      "com.atulkaushal.kafka.connect.github.QuestionValue";

  /** The Constant SCHEMA_VALUE_OWNER. */
  public static final String SCHEMA_VALUE_OWNER = "com.atulkaushal.kafka.connect.github.OwnerValue";

  /** The Constant KEY_SCHEMA. */
  // Key Schema
  public static final Schema KEY_SCHEMA =
      SchemaBuilder.struct()
          .name(SCHEMA_KEY)
          .version(1)
          .field(QUESTION_ID_FIELD, Schema.INT64_SCHEMA)
          .build();

  /** The Constant OWNER_SCHEMA. */
  // Value Schema
  public static final Schema OWNER_SCHEMA =
      SchemaBuilder.struct()
          .name(SCHEMA_VALUE_OWNER)
          .version(1)
          .field(OWNER_FIELD, Schema.STRING_SCHEMA)
          .field(USER_ID_FIELD, Schema.INT32_SCHEMA)
          .field(DISPLAY_NAME_FIELD, Schema.STRING_SCHEMA)
          .build();

  /** The Constant VALUE_SCHEMA. */
  public static final Schema VALUE_SCHEMA =
      SchemaBuilder.struct()
          .name(SCHEMA_VALUE_QUESTION)
          .version(2)
          .field(TAGS_FIELD, Schema.STRING_SCHEMA)
          .field(IS_ANSWERED_FIELD, Schema.BOOLEAN_SCHEMA)
          .field(TITLE_FIELD, Schema.STRING_SCHEMA)
          .field(VIEW_COUNT_FIELD, Schema.INT32_SCHEMA)
          .field(ANSWER_COUNT_FIELD, Schema.INT32_SCHEMA)
          .field(SCORE_FIELD, Schema.INT32_SCHEMA)
          .field(LAST_ACTIVITY_DATE_FIELD, Timestamp.SCHEMA)
          .field(CREATION_DATE_FIELD, Timestamp.SCHEMA)
          .field(QUESTION_ID_FIELD, Schema.INT64_SCHEMA)
          .field(CONTENT_LICENSE_FIELD, Schema.STRING_SCHEMA)
          .field(LINK_FIELD, Schema.STRING_SCHEMA)
          .field(TITLE_FIELD, Schema.STRING_SCHEMA)
          .field(OWNER_FIELD, OWNER_SCHEMA)
          .build();
}
