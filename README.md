# SuperMarket

A console-based supermarket management system built with Java, featuring role-based access control, inventory management, shopping cart, and sales reporting.

## Features

- **Role-Based Access** — 5 user roles (Admin, Manager, Cashier, Customer, Guest) with distinct permissions
- **Authentication & Registration** — Secure login with SHA-256 salted password hashing, credential validation, and 3-attempt login limit
- **Inventory Management** — Add, search, and manage products with low-stock alerts
- **Shopping Cart** — Customer and cashier cart with stock validation at checkout
- **Sales Transactions** — Automated transaction recording with CSV persistence
- **Sales Reporting** — Revenue totals, per-product sales leaderboard, and transaction history
- **First-Run Bootstrap** — Auto-prompts for admin creation on first launch

## Tech Stack

- Java 20
- Maven (with `maven-shade-plugin` for executable JAR)
- CSV file persistence
- Console UI (Scanner)

## Architecture

```
sa.edu.uqu.supermarket/
├── SuperMarket.java          # Entry point
├── registration/             # Auth, user management, password hashing
├── inventory/                # Items, inventory, shopping cart
├── sales/                    # Transactions and reporting
├── menu/                     # Menu interface and factory
└── user/                     # Role-specific menus (Admin, Manager, Cashier, Customer, Guest)
```

## Design Patterns

| Pattern | Where |
|---------|-------|
| Singleton | `AccountsManager`, `InventoryManager`, `SalesManager` |
| Factory Method | `MenuFactory` creates role-specific menus |
| Strategy | `Menu` interface with role implementations |
| Inheritance | `Person` → `User` |

## Data Persistence

All data is stored in CSV files in the working directory:

| File | Content |
|------|---------|
| `users.csv` | User accounts with hashed passwords |
| `inventory.csv` | Product catalog |
| `sales.csv` | Transaction history |

## User Roles

| Role | Capabilities |
|------|-------------|
| **Admin** | Manage users, manage inventory, assign roles |
| **Manager** | Check low stock, view sales reports, update stock |
| **Cashier** | Process transactions for walk-in customers |
| **Customer** | Browse products, manage cart, checkout |
| **Guest** | View products only, prompted to register |

## Getting Started

### Prerequisites

- Java 20+
- Maven 3.6+

### Build

```bash
mvn clean package
```

### Run

```bash
java -jar target/supermarket-1.0-SNAPSHOT.jar
```

On first run, you'll be prompted to create an Admin account.

### Login Flow

1. Enter username and password
2. Upon successful authentication, you're routed to your role's menu
3. Guests can browse products and register
4. Maximum 3 login attempts before forced exit

## Security

- Passwords hashed with SHA-256 + random 16-byte salt
- No plaintext passwords stored
- Credential validation (username: 3-16 chars, password: 8+ chars, valid email, 10-digit phone)
- Login attempt limiting

## Project Structure

| Component | Classes |
|-----------|---------|
| Authentication | `Authentication`, `AccountsManager`, `CredentialValidator`, `HashUtil` |
| Domain Objects | `User`, `Person`, `Role`, `Item`, `Transaction` |
| Business Logic | `InventoryManager`, `SalesManager`, `Cart` |
| UI/Navigation | `Menu`, `MenuFactory`, role-specific menus |
