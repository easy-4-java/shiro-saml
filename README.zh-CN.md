# shiro-saml-extension

[English](./README.md) | [简体中文](./README.zh-CN.md)

![Java](https://img.shields.io/badge/Java-17-orange) ![License](https://img.shields.io/badge/License-Apache%202.0-blue)

Apache Shiro 的 SAML 认证扩展，基于 OpenSAML 3 与 `shiro-biz`。提供 SAML 认证 Token、SAML 感知的 Subject 工厂、OpenSAML 对象构建工具与 SAML 专属认证异常。

## 目录

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

**是什么**

`shiro-saml-extension` 为基于 Shiro 的应用补充 SAML 构建块：

- `SamlToken` / `Saml2Token` —— `HostAuthenticationToken` 实现，其主体与凭证即 SAML 请求（断言）字符串，携带 host 与记住我状态。
- `SamlSubjectFactory` —— 以会话创建开启/关闭模式为 SAML 流程创建 Shiro Subject。
- `OpenSAMLUtils` —— SAML 对象构建（`buildSAMLObject`）、元素创建（`create`）与安全随机 ID 生成（`generateSecureRandomId`）工具。
- `AuthnContextComparisonType` —— 认证上下文比较类型枚举。
- SAML 专属异常：`ExpiredSamlException`、`IncorrectSamlException`、`InvalidSamlToken`、`NotObtainedSamlException`。

**不是什么**

- 它不是完整的 SAML IdP/SP 实现——SAML 协议处理（重定向绑定、断言校验、签名验证）需要在应用层基于 OpenSAML 构建。
- 它不是 Spring Boot Starter；不随包提供自动配置。

**典型场景**

| 场景 | 说明 |
| :--- | :--- |
| SAML SP 登录流程 | 用 `OpenSAMLUtils` 构建 SP 侧 SAML 请求，以 `SamlToken` 提交给 Shiro 认证。 |
| 认证上下文强制 | 使用 `AuthnContextComparisonType` 表达所需认证上下文的比较方式。 |
| 断言 ID 生成 | 使用 `OpenSAMLUtils.generateSecureRandomId()` 生成 `AssertionID` / 请求 ID。 |

## 2. Features & Status

| 能力 | 状态 | 说明 |
| :--- | :--- | :--- |
| SAML Token | 可用 | `SamlToken(String host, String SAMLRequest, boolean isRememberMe)`；`Saml2Token`（SAML 2.0 风格）。 |
| Subject 工厂 | 可用 | `SamlSubjectFactory(boolean sessionCreationEnabled)`。 |
| OpenSAML 工具 | 可用 | `OpenSAMLUtils` 中的 `buildSAMLObject`、`create`、`generateSecureRandomId`。 |
| 认证上下文比较 | 可用 | `AuthnContextComparisonType` 枚举。 |
| SAML 异常 | 可用 | `ExpiredSamlException`、`IncorrectSamlException`、`InvalidSamlToken`、`NotObtainedSamlException`。 |

> 状态以 `feature/2.0.x` 分支上的 `2.0.x.x.20260630-SNAPSHOT` 为准。

## 3. Requirements & Compatibility

| 项目 | 版本 |
| :--- | :--- |
| JDK | 17+ |
| Maven | 3.0+（内置 Maven Wrapper 3.5.0） |
| Apache Shiro | 1.13.0（`shiro-core`、`shiro-web`） |
| OpenSAML | 3.4.2（`opensaml-core`） |
| OpenSAML 支撑库 | 7.3.0（`java-support`） |
| easy4j 依赖 | `io.github.easy4j:shiro-biz`（`2.0.x.x.20260630-SNAPSHOT`） |

**版本线**

| 分支 | JDK 基线 | 版本模式 |
| :--- | :--- | :--- |
| `feature/1.0.x` | JDK 8 | `1.0.x.*` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` |

## 4. Architecture & Modules

```text
 IdP（SAML Response / Assertion）
        |
        v
 SamlToken / Saml2Token（host + SAMLRequest、rememberMe）
        |
        v
 Shiro SecurityManager
        |  SamlSubjectFactory（会话开关）
        v
 Subject（SAML 主体）
        |
        +-- OpenSAMLUtils（buildSAMLObject / create / ID）
        +-- AuthnContextComparisonType（上下文强制）
```

本项目为**单模块**工程（packaging 为 `jar`），类位于 `org.apache.shiro.spring.boot.saml`：

| 包 | 职责 |
| :--- | :--- |
| 根包 | `SamlSubjectFactory`、`AuthnContextComparisonType` |
| `token` | `SamlToken`、`Saml2Token` |
| `utils` | `OpenSAMLUtils` |
| `exception` | SAML 专属认证异常 |

## 5. Installation

该构件尚未发布到 Maven Central。请从项目配置的制品仓库（阿里云制品仓库）获取，或从源码本地安装；`feature/2.0.x` 分支当前使用的快照版本为 `2.0.x.x.20260630-SNAPSHOT`。

**Maven**

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>shiro-saml-extension</artifactId>
    <version>2.0.x.x.20260630-SNAPSHOT</version>
</dependency>
```

**Gradle**

```groovy
implementation 'io.github.easy4j:shiro-saml-extension:2.0.x.x.20260630-SNAPSHOT'
```

## 6. Quick Start

将 SAML 请求作为认证 Token 提交，并创建 SAML 感知的 Subject 工厂：

```java
import org.apache.shiro.spring.boot.saml.SamlSubjectFactory;
import org.apache.shiro.spring.boot.saml.token.SamlToken;

// 1. 携带 SAML 请求字符串的 Token
SamlToken token = new SamlToken("192.168.1.10", "<samlp:AuthnRequest .../>", false);

// 2. 面向 SAML 流程的 Subject 工厂（启用会话）
SamlSubjectFactory subjectFactory = new SamlSubjectFactory(true);
```

**预期结果：** `SamlToken` 可通过 `subject.login(token)` 提交；`SamlSubjectFactory` 控制认证主体是否创建会话。协议级处理（断言校验）仍在应用侧完成。

## 7. Configuration

本库没有配置属性与前缀。`SamlSubjectFactory` 通过构造器 `SamlSubjectFactory(boolean sessionCreationEnabled)` 配置。

## 8. Core Usage / API

| 类 | 包 | 职责 |
| :--- | :--- | :--- |
| `SamlToken` | `org.apache.shiro.spring.boot.saml.token` | `HostAuthenticationToken`；主体/凭证 = `SAMLRequest` 字符串。 |
| `Saml2Token` | `org.apache.shiro.spring.boot.saml.token` | SAML 2.0 风格的 `HostAuthenticationToken`。 |
| `SamlSubjectFactory` | `org.apache.shiro.spring.boot.saml` | 面向 SAML 流程的 `SessionCreationEnabledSubjectFactory` 子类。 |
| `OpenSAMLUtils` | `org.apache.shiro.spring.boot.saml.utils` | `generateSecureRandomId()`、`buildSAMLObject(Class)`、`create(Class, QName)`。 |
| `AuthnContextComparisonType` | `org.apache.shiro.spring.boot.saml` | 认证上下文比较类型枚举。 |
| `ExpiredSamlException` / `IncorrectSamlException` / `InvalidSamlToken` / `NotObtainedSamlException` | `org.apache.shiro.spring.boot.saml.exception` | SAML 认证异常。 |

```java
import org.apache.shiro.spring.boot.saml.utils.OpenSAMLUtils;

String assertionId = OpenSAMLUtils.generateSecureRandomId();
```

## 9. Testing & Build

```bash
# 完整构建（含 JaCoCo 覆盖率报告/检查）
./mvnw clean verify

# 安装到本地仓库
./mvnw install
```

测试与门禁事实（以 pom 配置为准）：

- 本模块暂无单元测试。
- JaCoCo 绑定 `prepare-agent` / `report` / `check`；`check` 规则要求**行覆盖率不低于 90%**（配置了 `haltOnFailure=false`）。

## 10. Versioning & Branches

| 分支 | JDK 基线 | 版本模式 | 状态 |
| :--- | :--- | :--- | :--- |
| `feature/1.0.x` | JDK 8 | `1.0.x.*` | 活跃；当前快照 `1.0.x.20260630-SNAPSHOT` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` | 维护中 |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` | 维护中 |

维护策略：1.0.x 版本线保持 JDK 8 兼容，服务于存量部署；2.0.x 与 3.0.x 版本线为现代 JDK 基线。发布制品发布到项目配置的制品仓库（阿里云制品仓库）与 GitHub Releases；项目尚未发布到 Maven Central。

## 11. Contributing & License

欢迎参与贡献——请在 [GitHub 仓库](https://github.com/easy-4-java/shiro-saml-extension) 提交 Issue 或 Pull Request。

本项目基于 **Apache License 2.0** 开源。详见 [LICENSE](LICENSE)。
