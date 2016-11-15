Feature: see LEDs
  As a user
  I want to see the humidity level on the LEDs
  So that I know when to water the plant

  Scenario: see red LED
    Given The PI and the sensors are installed
    And   The system works
    And   LEDs are enabled
    When  I water my plant
    Then  The LED turns yellow

  Scenario: see yellow LED
    Given The PI and the sensors are installed
    And   The system works
    And   LEDs are enabled
    When  I water my plant
    Then  The LED turns green

  Scenario: see green LED
    Given The PI and the sensors are installed
    And   The system works
    And   LEDs are enabled
    When  I wait
    Then  The LED turns yellow
