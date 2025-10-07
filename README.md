# MyQR - Wear OS QR Payment App

Quick access to bank QR codes on your smartwatch for seamless digital payments.

## Features

- Quick access of your QR code display
- Add custom bank accounts
- Long-press to delete saved banks
- Persistent local storage
- Optimized for Wear OS

## Architecture

```
┌─────────────────────────────────────────────────────────┐
│                   PRESENTATION LAYER                    │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐    │
│  │ BankList    │  │  AddBank    │  │  QRCode     │    │
│  │  Screen     │  │   Screen    │  │   Screen    │    │
│  └─────────────┘  └─────────────┘  └─────────────┘    │
└────────────────────────┬────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────┐
│                     DOMAIN LAYER                        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐    │
│  │  Use Cases  │  │ Repository  │  │  Business   │    │
│  │             │  │  Interface  │  │    Logic    │    │
│  └─────────────┘  └─────────────┘  └─────────────┘    │
└────────────────────────┬────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────┐
│                      DATA LAYER                         │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐    │
│  │ Repository  │  │  DataStore  │  │    JSON     │    │
│  │    Impl     │  │             │  │   Assets    │    │
│  └─────────────┘  └─────────────┘  └─────────────┘    │
└─────────────────────────────────────────────────────────┘
```

## Tech Stack

```
Wear OS  →  Jetpack Compose  →  Hilt DI  →  DataStore  →  Kotlinx Serialization  →  ZXing QR
```

## Project Structure

```
app/
├── data/               # Data models & storage
│   ├── Bank.kt
│   ├── BankDataStore.kt
│   └── BankRepositoryImpl.kt
├── domain/             # Business logic
│   ├── repository/
│   └── usecase/
└── presentation/       # UI & ViewModels
    ├── BankListScreen.kt
    ├── AddBankScreen.kt
    └── viewmodel/
```

## Quick Start

```bash
# Clone and build
git clone https://github.com/nyinyiz/MyQR.git
cd MyQR
./gradlew build

# Run on Wear OS device/emulator
./gradlew installDebug
```

## Usage Flow

```
┌──────┐
│ User │
└───┬──┘
    │
    │ Launch App
    ├────────────────────► ┌───────────┐
    │                       │ BankList  │
    │ ◄─────────────────────┤  Screen   │
    │   Show Banks          └─────┬─────┘
    │                             │
    │ Tap Bank                    │
    ├─────────────────────────────┤
    │                             │ Navigate
    │                             ▼
    │                       ┌───────────┐
    │ ◄─────────────────────┤  QRCode   │
    │   Display QR          │  Screen   │
    │                       └───────────┘
    │
    │ Tap + Add
    ├────────────────────► ┌───────────┐
    │                       │  AddBank  │
    │  Fill Details         │  Screen   │
    ├──────────────────────►│           │
    │                       └─────┬─────┘
    │ ◄─────────────────────────┬─┘
    │   Save & Return           │
    ▼                           ▼
```

## Configuration

Default banks in `app/src/main/res/raw/banks.json`:

```json
[
  {
    "id": 1,
    "name": "Sample Bank",
    "accountName": "John Doe",
    "logoColor": "FF00A651",
    "qrCodeData": "00020101021..."
  }
]
```

**Custom Bank IDs**: Start from 1000 (auto-assigned)

## Key Features Explained

| Feature | Description | Implementation |
|---------|-------------|----------------|
| **View Banks** | Browse saved banks | `BankListScreen.kt` |
| **Add Bank** | Create custom banks | `AddBankScreen.kt` |
| **Delete Bank** | Long-press (ID ≥ 1000) | `BankListScreen.kt:97-101` |
| **QR Display** | Show scannable QR code | `QRCodeScreen.kt` |

## Data Flow

```
┌─────────────┐                                    ┌─────────────┐
│JSON Assets  │───► Default Banks ───┐             │  DataStore  │
└─────────────┘                      │             └──────┬──────┘
                                     │                    │
                                     ▼                    ▼
                              ┌──────────────────────────────┐
                              │       Repository             │
                              └──────────────┬───────────────┘
                                             │
                                             ▼
                              ┌──────────────────────────────┐
                              │        Use Cases             │
                              └──────────────┬───────────────┘
                                             │
                                             ▼
                              ┌──────────────────────────────┐
                              │        ViewModel             │
                              └──────────────┬───────────────┘
                                             │
                                             ▼
                              ┌──────────────────────────────┐
                              │         UI Screen            │
                              └──────────────────────────────┘

Custom Banks ────────────────────────────────────────┘
```

## Contributing

1. Fork the repo
2. Create feature branch (`git checkout -b feature/Feature`)
3. Commit changes (`git commit -m 'Add Feature'`)
4. Push (`git push origin feature/Feature`)
5. Open Pull Request

## Author

Created by **Nyi Nyi Zaw** (nyinyizaw.dev@gmail.com)
