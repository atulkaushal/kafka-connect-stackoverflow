package com.github.atulkaushal.kafka.connect.stackoverflow.model;

import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.ANSWER_COUNT_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.CREATION_DATE_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.IS_ANSWERED_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.LAST_ACTIVITY_DATE_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.LINK_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.OWNER_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.QUESTION_ID_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.SCORE_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.TITLE_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.VIEW_COUNT_FIELD;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

/**
 * The Class Question.
 *
 * @author Atul
 */
public class Question {

  /** The tags. */
  private Set<String> tags;

  /** The is answered. */
  private boolean isAnswered;

  /** The view count. */
  private Integer viewCount;

  /** The answer count. */
  private Integer answerCount;

  /** The score. */
  private Integer score;

  /** The last activity date. */
  private Instant lastActivityDate;

  /** The creation date. */
  private Instant creationDate;

  /** The question id. */
  private Long questionId;

  /** The content license. */
  private String contentLicense;

  /** The link to question. */
  private String linkToQuestion;

  /** The title. */
  private String title;

  /** The additional properties. */
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** The owner. */
  private Owner owner;

  /** Instantiates a new question. */
  public Question() {}

  /**
   * Instantiates a new question.
   *
   * @param tags the tags
   * @param isAnswered the is answered
   * @param viewCount the view count
   * @param answerCount the answer count
   * @param score the score
   * @param lastActivityDate the last activity date
   * @param creationDate the creation date
   * @param questionId the question id
   * @param contentLicense the content license
   * @param linkToQuestion the link to question
   * @param title the title
   */
  public Question(
      Set<String> tags,
      boolean isAnswered,
      Integer viewCount,
      Integer answerCount,
      Integer score,
      Instant lastActivityDate,
      Instant creationDate,
      Long questionId,
      String contentLicense,
      String linkToQuestion,
      String title) {
    super();
    this.tags = tags;
    this.isAnswered = isAnswered;
    this.viewCount = viewCount;
    this.answerCount = answerCount;
    this.score = score;
    this.lastActivityDate = lastActivityDate;
    this.creationDate = creationDate;
    this.questionId = questionId;
    this.contentLicense = contentLicense;
    this.linkToQuestion = linkToQuestion;
    this.title = title;
  }

  /**
   * Gets the tags.
   *
   * @return the tags
   */
  public Set<String> getTags() {
    return tags;
  }

  /**
   * Sets the tags.
   *
   * @param tags the new tags
   */
  public void setTags(Set<String> tags) {
    this.tags = tags;
  }

  /**
   * Checks if is answered.
   *
   * @return true, if is answered
   */
  public boolean isAnswered() {
    return isAnswered;
  }

  /**
   * Sets the answered.
   *
   * @param isAnswered the new answered
   */
  public void setAnswered(boolean isAnswered) {
    this.isAnswered = isAnswered;
  }

  /**
   * With is answered.
   *
   * @param isAnswered the is answered
   * @return the question
   */
  public Question withIsAnswered(boolean isAnswered) {
    this.isAnswered = isAnswered;
    return this;
  }

  /**
   * Gets the view count.
   *
   * @return the view count
   */
  public Integer getViewCount() {
    return viewCount;
  }

  /**
   * Sets the view count.
   *
   * @param viewCount the new view count
   */
  public void setViewCount(Integer viewCount) {
    this.viewCount = viewCount;
  }

  /**
   * With view count.
   *
   * @param viewCount the view count
   * @return the question
   */
  public Question withViewCount(Integer viewCount) {
    this.viewCount = viewCount;
    return this;
  }

  /**
   * Gets the answer count.
   *
   * @return the answer count
   */
  public Integer getAnswerCount() {
    return answerCount;
  }

  /**
   * Sets the answer count.
   *
   * @param answerCount the new answer count
   */
  public void setAnswerCount(Integer answerCount) {
    this.answerCount = answerCount;
  }

  /**
   * With answer count.
   *
   * @param answerCount the answer count
   * @return the question
   */
  public Question withAnswerCount(Integer answerCount) {
    this.answerCount = answerCount;
    return this;
  }

  /**
   * Gets the score.
   *
   * @return the score
   */
  public Integer getScore() {
    return score;
  }

  /**
   * Sets the score.
   *
   * @param score the new score
   */
  public void setScore(Integer score) {
    this.score = score;
  }

  /**
   * With score.
   *
   * @param score the score
   * @return the question
   */
  public Question withScore(Integer score) {
    this.score = score;
    return this;
  }

  /**
   * Gets the last activity date.
   *
   * @return the last activity date
   */
  public Instant getLastActivityDate() {
    return lastActivityDate;
  }

  /**
   * Sets the last activity date.
   *
   * @param lastActivityDate the new last activity date
   */
  public void setLastActivityDate(Instant lastActivityDate) {
    this.lastActivityDate = lastActivityDate;
  }

  /**
   * With last activity date.
   *
   * @param lastActivityDate the last activity date
   * @return the question
   */
  public Question withLastActivityDate(Instant lastActivityDate) {
    this.lastActivityDate = lastActivityDate;
    return this;
  }

  /**
   * Gets the creation date.
   *
   * @return the creation date
   */
  public Instant getCreationDate() {
    return creationDate;
  }

  /**
   * Sets the creation date.
   *
   * @param creationDate the new creation date
   */
  public void setCreationDate(Instant creationDate) {
    this.creationDate = creationDate;
  }

  /**
   * With creation date.
   *
   * @param creationDate the creation date
   * @return the question
   */
  public Question withCreationDate(Instant creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Gets the question id.
   *
   * @return the question id
   */
  public Long getQuestionId() {
    return questionId;
  }

  /**
   * Sets the question id.
   *
   * @param questionId the new question id
   */
  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }

  /**
   * With question id.
   *
   * @param questionId the question id
   * @return the question
   */
  public Question withQuestionId(long questionId) {
    this.questionId = questionId;
    return this;
  }

  /**
   * Gets the content license.
   *
   * @return the content license
   */
  public String getContentLicense() {
    return contentLicense;
  }

  /**
   * Sets the content license.
   *
   * @param contentLicense the new content license
   */
  public void setContentLicense(String contentLicense) {
    this.contentLicense = contentLicense;
  }

  /**
   * With content license.
   *
   * @param contentLicense the content license
   * @return the question
   */
  public Question withContentLicense(String contentLicense) {
    this.contentLicense = contentLicense;
    return this;
  }

  /**
   * Gets the link to question.
   *
   * @return the link to question
   */
  public String getLinkToQuestion() {
    return linkToQuestion;
  }

  /**
   * Sets the link to question.
   *
   * @param linkToQuestion the new link to question
   */
  public void setLinkToQuestion(String linkToQuestion) {
    this.linkToQuestion = linkToQuestion;
  }

  /**
   * With link to question.
   *
   * @param linkToQuestion the link to question
   * @return the question
   */
  public Question withLinkToQuestion(String linkToQuestion) {
    this.linkToQuestion = linkToQuestion;
    return this;
  }

  /**
   * Gets the title.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title.
   *
   * @param title the new title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * With title.
   *
   * @param title the title
   * @return the question
   */
  public Question withTitle(String title) {
    this.title = title;
    return this;
  }

  /**
   * Gets the additional properties.
   *
   * @return the additional properties
   */
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  /**
   * Sets the additional properties.
   *
   * @param additionalProperties the additional properties
   */
  public void setAdditionalProperties(Map<String, Object> additionalProperties) {
    this.additionalProperties = additionalProperties;
  }

  /**
   * With additional properties.
   *
   * @param additionalProperties the additional properties
   * @return the question
   */
  public Question withAdditionalProperties(Map<String, Object> additionalProperties) {
    this.additionalProperties = additionalProperties;
    return this;
  }

  /**
   * Gets the owner.
   *
   * @return the owner
   */
  public Owner getOwner() {
    return owner;
  }

  /**
   * Sets the owner.
   *
   * @param owner the new owner
   */
  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  /**
   * With owner.
   *
   * @param owner the owner
   * @return the question
   */
  public Question withOwner(Owner owner) {
    this.owner = owner;
    return this;
  }

  /**
   * With additional property.
   *
   * @param name the name
   * @param value the value
   * @return the question
   */
  public Question withAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
    return this;
  }

  /**
   * From json.
   *
   * @param jsonObject the json object
   * @return the question
   */
  public static Question fromJson(JSONObject jsonObject) {

    Question question = new Question();
    question.withAnswerCount(jsonObject.getInt(ANSWER_COUNT_FIELD));
    // question.withContentLicense(jsonObject.getString(CONTENT_LICENSE_FIELD));
    question.withCreationDate(Instant.ofEpochSecond(jsonObject.getLong(CREATION_DATE_FIELD)));
    question.withIsAnswered(jsonObject.getBoolean(IS_ANSWERED_FIELD));
    question.withLastActivityDate(
        Instant.ofEpochSecond(jsonObject.getLong(LAST_ACTIVITY_DATE_FIELD)));
    question.withLinkToQuestion(jsonObject.getString(LINK_FIELD));
    question.withQuestionId(jsonObject.getInt(QUESTION_ID_FIELD));
    question.withScore(jsonObject.getInt(SCORE_FIELD));
    question.withTitle(jsonObject.getString(TITLE_FIELD));
    question.withViewCount(jsonObject.getInt(VIEW_COUNT_FIELD));

    // Owner is mandatory
    Owner owner = Owner.fromJson(jsonObject.getJSONObject(OWNER_FIELD));
    question.withOwner(owner);

    return question;
  }
}
