# Code Challenge: Authorizer 💳

## _Command-line application with Java 11_

The objective of this challenge is to implement an application that authorizes locks for a specific account, following a series of predefined rules.

## How the program works

The program will input lines in `json` format on standard input (`stdin`) and must provide output in json format for each of the inputs.

## Authorizer Operations

The program handles two types of operations, deciding which one to execute according to the line being processed:

1. Account creation
2. Authorizing an account transaction

### 1. Account creation

#### Input

Attempts to authorize a transaction for a certain `merchant`, `amount` (transaction amount) and `time` (transaction time) according to the status of the created account and the last transactions that were authorized.

#### Output

The current state of the account along with any business logic violations. If there are no violations in the processing the operation, the `violations` field must return an empty vector `[]`.

#### Business logic violations

- No transactions should be accepted until the account has been initialized: `account-not-initialized`
- No transactions should be accepted when the card is not active: `card-not-active`
- Transaction amount must not exceed available limit: `insufficient-limit`
- There should be no more than 3 transactions from any merchant in a 2 minute interval: `high-frequency-small-interval`
- There must be no more than 1 similar transaction (same value and merchant) within 2 minutes: `double-transaction`

### 2. Authorizing an account transaction

#### Input

Creates the account with the attributes `available-limit` and `active-card`. For this project we are assuming that the Authorizer will handle only one account.

#### Output

The current state of the created account along with any business logic violations. If there are no violations in operation processing, the `violations` field must return an empty vector `[]` .

#### Business logic violations

Once created, the account must not be updated or recreated. If the application receives a second account creation operation, it should return the following violation: `account-already-initialized`.

### Example

Input:

```
{"transaction": {"merchant": "Burger King", "amount": 20, "time": "2021-08-20T15:30:00.000Z"}}
{"account": {"active-card": true, "available-limit": 100}}
{"transaction": {"merchant": "Habbib's", "amount": 200, "time": "2021-08-21T15:30:00.000Z"}}
{"transaction": {"merchant": "Burger King", "amount": 20, "time": "2021-08-25T15:30:00.000Z"}}
{"transaction": {"merchant": "Burger King", "amount": 25, "time": "2021-08-25T15:30:05.000Z"}}
{"transaction": {"merchant": "Burger King", "amount": 30, "time": "2021-08-25T15:30:10.000Z"}}
{"transaction": {"merchant": "Burger King", "amount": 10, "time": "2021-08-25T15:30:20.000Z"}}
{"transaction": {"merchant": "Burger King", "amount": 20, "time": "2021-08-25T15:31:10.000Z"}}
{"transaction": {"merchant": "Burger King", "amount": 10, "time": "2021-08-30T15:00:00.000Z"}}
{"transaction": {"merchant": "Burger King", "amount": 10, "time": "2021-08-30T15:01:00.000Z"}}
{"transaction": {"merchant": "Habbib's", "amount": 90, "time": "2021-08-30T16:00:00.000Z"}}
{"transaction": {"merchant": "McDonald's", "amount": 30, "time": "2021-08-30T17:00:00.000Z"}}
{"account": {"active-card": true, "available-limit": 200}}
{"transaction": {"merchant": "McDonald's", "amount": 5, "time": "2021-08-31T18:00:00.000Z"}}
```

Expected output:

```
{"account":{},"violations":["account-not-initialized"]}
{"account":{"active-card":true,"available-limit":100},"violations":[]}
{"account":{"active-card":true,"available-limit":100},"violations":["insufficient-limit"]}
{"account":{"active-card":true,"available-limit":80},"violations":[]}
{"account":{"active-card":true,"available-limit":55},"violations":[]}
{"account":{"active-card":true,"available-limit":25},"violations":[]}
{"account":{"active-card":true,"available-limit":25},"violations":["high-frequency-small-interval"]}
{"account":{"active-card":true,"available-limit":25},"violations":["high-frequency-small-interval","double-transaction"]}
{"account":{"active-card":true,"available-limit":15},"violations":[]}
{"account":{"active-card":true,"available-limit":15},"violations":["double-transaction"]}
{"account":{"active-card":true,"available-limit":15},"violations":["insufficient-limit"]}
{"account":{"active-card":true,"available-limit":15},"violations":["insufficient-limit"]}
{"account":{"active-card":true,"available-limit":15},"violations":["account-already-initialized"]}
{"account":{"active-card":true,"available-limit":10},"violations":[]}
```

## Application status

The program does not depend on any external database, and the application's internal state is managed in memory. the state of application will be empty whenever the application is started.

Authorizer operations that have unsaved violations in the internal state of the application.

# Project architecture

This project was developed using the best development practices and structured through Clean Architecture following the principles of S.O.L.I.D.

> The idea of Clean Architecture is to put delivery and gateway at the edges of our design. Business logic should not depend on whether we expose a REST or a GraphQL API, and it should not depend on where we get data from — a database, a microservice API exposed via gRPC or REST, or just a simple CSV file.

## External library

- [Jackson] (https://github.com/FasterXML/jackson) - Used for manipulate JSON structure.

# Running application

System requeriments:

- Java 11
- Maven

Compiling project:
```
mvn clean compile assembly:single
```

Running application:
```
java -jar target/authorize.jar < operations.json
```
