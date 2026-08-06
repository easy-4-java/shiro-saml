# shiro-saml-extension

![Java](https://img.shields.io/badge/Java-8-orange) ![License](https://img.shields.io/badge/License-Apache%202.0-blue)

SAML authentication extension for Apache Shiro, built on OpenSAML 3 and `shiro-biz`. It provides SAML authentication tokens, a SAML-aware subject factory, OpenSAML object-building utilities and SAML-specific authentication exceptions.

## Table of Contents

- [1. Project Overview](#1-project-overview)
- [2. Features & Status](#2-features--status)
- [3. Requirements & Compatibility](#3-requirements--compatibility)
- [4. Architecture & Modules](#4-architecture--modules)
- [5. Installation](#5-installation)
- [6. Quick Start](#6-quick-start)
- [7. Configuration](#7-configuration)
- [8. Core Usage / API](#8-core-usage--api)
- [9. Testing & Build](#9-testing--build)
- [10. Versioning & Branches](#10-versioning--branches)
- [11. Contributing & License](#11-contributing--license)

## 1. Project Overview

**What it is**

`shiro-saml-extension` adds SAML building blocks to Shiro-based applications:

- `SamlToken` / `Saml2Token` — `HostAuthenticationToken` implementations whose principal and credentials are the SAML request (assertion) string, carrying host and remember-me state.
- `SamlSubjectFactory` — creates Shiro subjects in a session-creation enabled/disabled mode for SAML flows.
- `OpenSAMLUtils` — helpers for SAML object building (`buildSAMLObject`), element creation (`create`) and secure random id generation (`generateSecureRandomId`).
- `AuthnContextComparisonType` — enum for authentication-context comparison types.
- SAML-specific exceptions: `ExpiredSamlException`, `IncorrectSamlException`, `InvalidSamlToken`, `NotObtainedSamlException`.

**What it is not**

- It is not a full SAML IdP/SP implementation — SAML protocol handling (redirect binding, assertion validation, signature checks) must be built in the application layer on top of OpenSAML.
- It is not a Spring Boot starter; no auto-configuration is shipped.

**Typical scenarios**

| Scenario | Description |
| :--- | :--- |
| SAML SP login flow | Build the SP-side SAML request with `OpenSAMLUtils`, submit it as a `SamlToken` to Shiro for authentication. |
| Authentication context enforcement | Use `AuthnContextComparisonType` to express the required authentication-context comparison. |
| Assertion id generation | `OpenSAMLUtils.generateSecureRandomId()` for `AssertionID` / request ids. |

## 2. Features & Status

| Capability | Status | Notes |
| :--- | :--- | :--- |
| SAML tokens | Available | `SamlToken(String host, String SAMLRequest, boolean isRememberMe)`; `Saml2Token` (SAML 2.0 flavor). |
| Subject factory | Available | `SamlSubjectFactory(boolean sessionCreationEnabled)`. |
| OpenSAML utilities | Available | `buildSAMLObject`, `create`, `generateSecureRandomId` in `OpenSAMLUtils`. |
| Authn context comparison | Available | `AuthnContextComparisonType` enum. |
| SAML exceptions | Available | `ExpiredSamlException`, `IncorrectSamlException`, `InvalidSamlToken`, `NotObtainedSamlException`. |

> Status is reported as of `1.0.x.20260630-SNAPSHOT` on the `feature/1.0.x` branch.

## 3. Requirements & Compatibility

| Item | Version |
| :--- | :--- |
| JDK | 8+ |
| Maven | 3.0+ (Maven Wrapper 3.5.0 bundled) |
| Apache Shiro | 1.13.0 (`shiro-core`, `shiro-web`) |
| OpenSAML | 3.4.2 (`opensaml-core`) |
| OpenSAML support lib | 7.3.0 (`java-support`) |
| easy4j dependency | `io.github.easy4j:shiro-biz` (`1.0.x.20260630-SNAPSHOT`) |

**Version lines**

| Branch | JDK baseline | Version pattern |
| :--- | :--- | :--- |
| `feature/1.0.x` | JDK 8 | `1.0.x.*` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` |

## 4. Architecture & Modules

```text
 IdP (SAML Response / Assertion)
        |
        v
 SamlToken / Saml2Token (host + SAMLRequest, rememberMe)
        |
        v
 Shiro SecurityManager
        |  SamlSubjectFactory (session on/off)
        v
 Subject (SAML principal)
        |
        +-- OpenSAMLUtils (buildSAMLObject / create / ids)
        +-- AuthnContextComparisonType (context enforcement)
```

This is a **single-module** project (packaging `jar`), classes under `org.apache.shiro.spring.boot.saml`:

| Package | Role |
| :--- | :--- |
| root | `SamlSubjectFactory`, `AuthnContextComparisonType` |
| `token` | `SamlToken`, `Saml2Token` |
| `utils` | `OpenSAMLUtils` |
| `exception` | SAML-specific authentication exceptions |

## 5. Installation

The artifact is not yet published to Maven Central. Resolve it from the project's configured artifact repository (Aliyun Packages) or install it locally from source; the snapshot version currently used on the `feature/1.0.x` branch is `1.0.x.20260630-SNAPSHOT`.

**Maven**

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>shiro-saml-extension</artifactId>
    <version>1.0.x.20260630-SNAPSHOT</version>
</dependency>
```

**Gradle**

```groovy
implementation 'io.github.easy4j:shiro-saml-extension:1.0.x.20260630-SNAPSHOT'
```

## 6. Quick Start

Submit a SAML request as an authentication token and create a SAML-aware subject factory:

```java
import org.apache.shiro.spring.boot.saml.SamlSubjectFactory;
import org.apache.shiro.spring.boot.saml.token.SamlToken;

// 1. Token carrying the SAML request string
SamlToken token = new SamlToken("192.168.1.10", "<samlp:AuthnRequest .../>", false);

// 2. Subject factory for the SAML flow (sessions enabled)
SamlSubjectFactory subjectFactory = new SamlSubjectFactory(true);
```

**Expected result:** the `SamlToken` can be submitted via `subject.login(token)`; the `SamlSubjectFactory` controls whether a session is created for the authenticated subject. Protocol-level processing (assertion validation) remains application-side.

## 7. Configuration

This library has no configuration properties or prefix. `SamlSubjectFactory` is configured via its constructor `SamlSubjectFactory(boolean sessionCreationEnabled)`.

## 8. Core Usage / API

| Class | Package | Role |
| :--- | :--- | :--- |
| `SamlToken` | `org.apache.shiro.spring.boot.saml.token` | `HostAuthenticationToken`; principal/credentials = `SAMLRequest` string. |
| `Saml2Token` | `org.apache.shiro.spring.boot.saml.token` | SAML 2.0-flavored `HostAuthenticationToken`. |
| `SamlSubjectFactory` | `org.apache.shiro.spring.boot.saml` | `SessionCreationEnabledSubjectFactory` subclass for SAML flows. |
| `OpenSAMLUtils` | `org.apache.shiro.spring.boot.saml.utils` | `generateSecureRandomId()`, `buildSAMLObject(Class)`, `create(Class, QName)`. |
| `AuthnContextComparisonType` | `org.apache.shiro.spring.boot.saml` | Authentication-context comparison type enum. |
| `ExpiredSamlException` / `IncorrectSamlException` / `InvalidSamlToken` / `NotObtainedSamlException` | `org.apache.shiro.spring.boot.saml.exception` | SAML authentication exceptions. |

```java
import org.apache.shiro.spring.boot.saml.utils.OpenSAMLUtils;

String assertionId = OpenSAMLUtils.generateSecureRandomId();
```

## 9. Testing & Build

```bash
# Full build with JaCoCo coverage report/check
./mvnw clean verify

# Install into the local repository
./mvnw install
```

Test & gate facts (as configured in the pom):

- No unit tests exist in this module yet.
- JaCoCo is bound to `prepare-agent` / `report` / `check`; the `check` rule requires a **90% line coverage ratio** (configured with `haltOnFailure=false`).

## 10. Versioning & Branches

| Branch | JDK baseline | Version pattern | Status |
| :--- | :--- | :--- | :--- |
| `feature/1.0.x` | JDK 8 | `1.0.x.*` | Active; current snapshot `1.0.x.20260630-SNAPSHOT` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` | Maintained |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` | Maintained |

Maintenance strategy: the 1.0.x line keeps JDK 8 compatibility for legacy deployments; the 2.0.x and 3.0.x lines are the modern JDK baselines. Release artifacts are published to the project's configured artifact repository (Aliyun Packages) and GitHub Releases; the project has not yet published to Maven Central.

## 11. Contributing & License

Contributions are welcome — please open an issue or a pull request on the [GitHub repository](https://github.com/easy-4-java/shiro-saml-extension).

This project is licensed under the **Apache License 2.0**. See [LICENSE](LICENSE) for details.
