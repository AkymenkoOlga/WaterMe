Feature: Change settings
  As a user
  I want to change the settings of the PI
  So that I can decide whether I want to receive notifications,
  hear sounds, see LEDs and set the Wifi


  Scenario: enter settings
    Given App is installed
    And   I see the main menu
    When  I click on settings
    Then  The settings displays

  Scenario: enable notifications
    Given I am in the settings
    And   The notifications are disabled
    When  I click on the switch button that is located next to notifications label
    Then  The switch button will be disabled

  Scenario: disable notifications
    Given I am in the settings
    And   The notifications are enabled
    When  I click on the switch button that is located next to notifications label
    Then  The switch button will be enabled

  Scenario: enable sounds
    Given I am in the settings
    And   The sounds are disabled
    When  I click on the switch button that is located next to sounds label
    Then  The switch button will be enabled
    And   I hear notification sounds

  Scenario: disable sounds
    Given I am in the settings
    And   The sounds are enabled
    When  I click on the switch button that is located next to sounds label
    Then  The switch button will be enabled
    And   I don't hear notification sounds anymore

  Scenario: enable LEDs
    Given I am in the settings
    And   The LEDs are disabled
    When  I click on the switch button that is located next to LEDs label
    Then  The switch button will be enabled
    And   The LEDs are turned off

  Scenario  disable LEDs
    Given I am in the settings
    And   The LEDs are enabled
    When  I click on the switch button that is located next to LEDs label
    Then  The switch button will be disabled
    And   The LEDs are turned off

  Scenario: enter WLAN menu
    Given I am in the settings
    When  I click on Raspberry WIFI
    Then  The WLAN menu displays

  Scenario: set WLAN
    Given I am in the Raspberry WIFI
    And   I know the SSID and password of my network
    When  I enter the SSID
    And   I enter the password
    And   I click on submit
    Then  I see "Settings changed"
    And   I see the new IP-address of the PI
