@Books
Feature: Store/Add/Delete/Edit books to Bookstore

  @StoreBookList
  Scenario: Get list of books and Store
    Given I get list of books in Bookstore
    Then I store the list of books

  @AddBooksToCart
  Scenario: Add books from bookstore to User cart
    Given I add a book to user cart
    And I verify given book already exists in users cart
    And I clear user cart
    When I add a book to user cart
    Then I verify book is added to user book list
