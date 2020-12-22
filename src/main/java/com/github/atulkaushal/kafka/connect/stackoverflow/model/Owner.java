package com.github.atulkaushal.kafka.connect.stackoverflow.model;

import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.DISPLAY_NAME_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.OWNER_LINK_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.REPUTATION_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.USER_ID_FIELD;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSchemas.USER_TYPE_FIELD;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * The Class Owner.
 *
 * @author Atul
 */
public class Owner {

  /** The reputation. */
  private Integer reputation;

  /** The user id. */
  private Long userId;

  /** The user type. */
  private String userType;

  /** The accept rate. */
  private String acceptRate;

  /** The display name. */
  private String displayName;

  /** The profile image URL. */
  private String profileImageURL;

  /** The profile link. */
  private String profileLink;

  /** The additional properties. */
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** Instantiates a new owner. */
  public Owner() {}

  /**
   * Instantiates a new owner.
   *
   * @param reputation the reputation
   * @param userId the user id
   * @param userType the user type
   * @param acceptRate the accept rate
   * @param displayName the display name
   * @param profileImageURL the profile image URL
   * @param profileLink the profile link
   */
  public Owner(
      Integer reputation,
      Long userId,
      String userType,
      String acceptRate,
      String displayName,
      String profileImageURL,
      String profileLink) {
    super();
    this.reputation = reputation;
    this.userId = userId;
    this.userType = userType;
    this.acceptRate = acceptRate;
    this.displayName = displayName;
    this.profileImageURL = profileImageURL;
    this.profileLink = profileLink;
  }

  /**
   * Gets the reputation.
   *
   * @return the reputation
   */
  public Integer getReputation() {
    return reputation;
  }

  /**
   * Sets the reputation.
   *
   * @param reputation the new reputation
   */
  public void setReputation(Integer reputation) {
    this.reputation = reputation;
  }

  /**
   * Gets the user id.
   *
   * @return the user id
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Sets the user id.
   *
   * @param userId the new user id
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Gets the user type.
   *
   * @return the user type
   */
  public String getUserType() {
    return userType;
  }

  /**
   * Sets the user type.
   *
   * @param userType the new user type
   */
  public void setUserType(String userType) {
    this.userType = userType;
  }

  /**
   * Gets the accept rate.
   *
   * @return the accept rate
   */
  public String getAcceptRate() {
    return acceptRate;
  }

  /**
   * Sets the accept rate.
   *
   * @param acceptRate the new accept rate
   */
  public void setAcceptRate(String acceptRate) {
    this.acceptRate = acceptRate;
  }

  /**
   * Gets the display name.
   *
   * @return the display name
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Sets the display name.
   *
   * @param displayName the new display name
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Gets the profile image URL.
   *
   * @return the profile image URL
   */
  public String getProfileImageURL() {
    return profileImageURL;
  }

  /**
   * Sets the profile image URL.
   *
   * @param profileImageURL the new profile image URL
   */
  public void setProfileImageURL(String profileImageURL) {
    this.profileImageURL = profileImageURL;
  }

  /**
   * Gets the profile link.
   *
   * @return the profile link
   */
  public String getProfileLink() {
    return profileLink;
  }

  /**
   * Sets the profile link.
   *
   * @param profileLink the new profile link
   */
  public void setProfileLink(String profileLink) {
    this.profileLink = profileLink;
  }

  /**
   * With additional property.
   *
   * @param name the name
   * @param value the value
   * @return the owner
   */
  public Owner withAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
    return this;
  }

  /**
   * From json.
   *
   * @param jsonObject the json object
   * @return the owner
   */
  public static Owner fromJson(JSONObject jsonObject) {
    Owner owner = new Owner();
    owner.setProfileLink(jsonObject.getString(OWNER_LINK_FIELD));
    owner.setReputation(jsonObject.getInt(REPUTATION_FIELD));
    owner.setUserId(jsonObject.getLong(USER_ID_FIELD));
    owner.setUserType(jsonObject.getString(USER_TYPE_FIELD));
    owner.setDisplayName(jsonObject.getString(DISPLAY_NAME_FIELD));
    return owner;
  }
}
