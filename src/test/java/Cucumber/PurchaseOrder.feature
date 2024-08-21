
  @tag
  Feature: Purchase Order from Ecommerce Website

    Background:
      Given I landed on Ecommerce page


    @tag2
    Scenario Outline: Positive Test of submitting the order
      Given Logged in with username <name> and password <password>
      When I add product <productName> to Cart
      And Checkout <productName> and submit the order
      Then "THANKYOU FOR THE ORDER." message is displayed on confirmation

      Examples:
        | name           | password  | productName |
        | toba@gmail.com | Kayode888 | ZARA COAT 3 |
