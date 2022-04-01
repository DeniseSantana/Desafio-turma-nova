@rest @api
Feature: rest tests
  Feature to test automation structure

  @API_00001
  Scenario: Validar autorizcao API Livros
    Given eu sou um usuario autorizado
    And valido a lista de livros disponivel
    
  @API_00002
  Scenario: Validar autorizcao API Livros
    Given eu sou um usuario autorizado
    And valido a lista de livros disponivel