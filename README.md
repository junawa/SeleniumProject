# SauceDemo Selenium Automation Framework
# Allure Report
<img width="726" height="522" alt="Allure Report" src="https://github.com/user-attachments/assets/2923b9c0-69e4-4667-9782-b943ee470b99" />

# Github Actions
<img width="943" height="419" alt="Github Actions" src="https://github.com/user-attachments/assets/c5769946-ecbb-4664-8cb7-14177906b66d" />

# Jenkins
<img width="940" height="410" alt="Jenkins" src="https://github.com/user-attachments/assets/93ccc9ab-8aad-49eb-8a78-e7678a03ba26" />

# SauceDemo
<img width="947" height="490" alt="SauceDemo" src="https://github.com/user-attachments/assets/0b7ddbf2-f7c3-4093-ad00-8c52da696413" />

A portfolio-quality Java automation framework for SauceDemo UI and API testing. It uses Selenium, TestNG, REST Assured, Maven, Allure, SLF4J/Logback, Page Objects, typed JSON test data, and CI pipelines.

## Architecture

```text
TestNG suite → test → workflow → page object / API client → Selenium / REST Assured
                            ↑
             configuration, data factory, waits, driver, logging, reporting
```

Tests state expected business outcomes. Workflows compose user actions. Pages encapsulate locators and browser interaction. API tests use `ApiClient` separately from UI tests.

## Technology stack

| Tool | Version |
|---|---:|
| Java | 21 LTS |
| Maven | 3.9.16+ |
| Selenium Java | 4.39.0 |
| TestNG | 7.12.0 |
| REST Assured | 6.0.1 |
| Allure Java | 2.35.4 |
| Jackson | 2.20.1 |

Selenium Manager resolves ChromeDriver or EdgeDriver; no driver binary is committed or manually configured.

## Prerequisites

- JDK 21 (`java -version`)
- Maven 3.9.16+ (`mvn -version`)
- Google Chrome and/or Microsoft Edge
- Git

Allure is provisioned by the Maven plugin when generating reports; an Allure CLI install is optional.

## Project layout

```text
src/main/java/com/saucedemo/framework/
  api/          REST Assured client and request specification
  base/         BaseTest and BasePage
  components/   Reusable site components
  config/       Environment configuration
  driver/       Thread-local WebDriver lifecycle
  listeners/    TestNG failure diagnostics
  pages/        Page Objects
  testdata/     Models, readers, and factory
  utils/        Waits, screenshots, dynamic values
  workflows/    Business-level user journeys
src/test/java/tests/{bvt,smoke,regression,e2e,api}/
src/test/resources/{config,testdata}/
```

## Execution

```powershell
mvn clean test -Dsuite=bvt
mvn clean test -Dsuite=smoke
mvn clean test -Dsuite=regression
mvn clean test -Dsuite=e2e
mvn clean test -Dsuite=api
```

Run headlessly, override a property, or select an environment:

```powershell
mvn clean test -Dsuite=bvt -Dheadless=true
mvn clean test -Dsuite=smoke -Denv=qa -Dbrowser=edge
mvn clean test -Dsuite=regression
```

The available XML suites are `testng-bvt.xml`, `testng-smoke.xml`, `testng-regression.xml`, `testng-e2e.xml`, and `testng-api.xml`. To run a single TestNG test class, pass `-Dtest=fully.qualified.ClassName` after temporarily selecting an applicable suite, or run it from the IDE.

## Configuration

Configuration files live under `src/test/resources/config`.

```properties
baseUrl=https://www.saucedemo.com/
browser=edge
timeout=10
headless=false
apiBaseUri=https://jsonplaceholder.typicode.com
```

Resolution order is system property, environment variable (for example `BASE_URL`), then selected environment file. Select the file with `-Denv=local`, `-Denv=qa`, or `-Denv=staging`.

## Test data and isolation

UI data is kept in JSON under `src/test/resources/testdata/ui`. Tests do not parse JSON or know filenames; they request immutable `UserData`, `ProductData`, or `CheckoutData` records from `TestDataFactory`.

API request payloads live under `testdata/api/requests`; expected data can live under `testdata/api/expected`. A `@DataProvider` receives typed data from the factory. Each factory request deserializes a fresh immutable object, and `RandomDataGenerator` creates unique values only when needed. This prevents cross-test mutation during parallel execution.

The SauceDemo credentials are publicly documented demo credentials, not production secrets. Do not commit real secrets. Supply secrets using environment variables, GitHub Secrets, or Jenkins Credentials; avoid logging credentials, tokens, cookies, or Authorization headers.

## Reporting and troubleshooting

Allure results are written to `target/allure-results`. Generate a static report with:

```powershell
mvn allure:report
```

Open `target/site/allure-maven/index.html`. On a UI failure, the TestNG listener logs the failure and attaches the current URL and screenshot to Allure. `categories.json` classifies common automation and environment failures. Logs are concise and emitted through SLF4J/Logback.

If Maven is not found, install Maven and add its `bin` directory to `PATH`. If Chrome startup fails in CI, use `-Dheadless=true`. Network/public-demo outages should be classified as environment failures rather than immediately treated as product defects.

## Parallel execution

Suites run TestNG classes in parallel with two workers by default. `DriverFactory` stores one browser in `ThreadLocal<WebDriver>` and always removes it after a test. Workflows are created per test, data records are immutable, and no test state is static and mutable. To change worker count, update the suite XML `thread-count` value cautiously.

## CI/CD

- Pull requests run BVT in `.github/workflows/bvt.yml`.
- Pushes to `develop` run Smoke.
- Weekday scheduled jobs run Regression.
- Each workflow uploads Surefire and Allure artifacts even on failure.
- `Jenkinsfile` offers `SUITE`, `BROWSER`, `ENVIRONMENT`, and `HEADLESS` parameters, archives evidence, and generates Allure output.

Recommended branching: `main`, `develop`, `feature/*`, `bugfix/*`, and `hotfix/*`. Keep commits focused and never commit `target/`, reports, screenshots, IDE state, local property files, or credentials.

## Future improvements

- Add Firefox support to `DriverFactory` when a real need exists.
- Add authenticated API environments and API-based test-data cleanup.
- Add schema validation and contract tests for a stable product API.
- Add a quality gate and scheduled CI notifications.
- Add retry only for diagnosed transient infrastructure failures; never use retry to hide test instability.
