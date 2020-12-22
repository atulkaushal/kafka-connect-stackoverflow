package com.github.atulkaushal.kafka.connect.stackoverflow.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

/**
 * The Class OwnerTest.
 *
 * @author Atul
 */
public class OwnerTest {

  /** The owner str. */
  String ownerStr =
      "{\r\n"
          + "        \"reputation\": 464,\r\n"
          + "        \"user_id\": 10461625,\r\n"
          + "        \"user_type\": \"registered\",\r\n"
          + "        \"profile_image\": \"https://i.stack.imgur.com/fgo5D.jpg?s=128&g=1\",\r\n"
          + "        \"display_name\": \"PatPatPat\",\r\n"
          + "        \"link\": \"https://stackoverflow.com/users/10461625/patpatpat\"\r\n"
          + "      }";

  /** The owner json. */
  private JSONObject ownerJson = new JSONObject(ownerStr);

  /** Can parse json. */
  @Test
  public void canParseJson() {
    Owner owner = Owner.fromJson(ownerJson);
    assertEquals(owner.getReputation(), (Integer) 464);
    assertEquals(owner.getProfileLink(), "https://stackoverflow.com/users/10461625/patpatpat");
    assertEquals(owner.getDisplayName(), "PatPatPat");
  }
}
