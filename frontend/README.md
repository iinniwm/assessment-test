# User Management Frontend

This is a React frontend application for the User Management RESTful API. It allows you to view, create, update, and delete users.

## Features

- View all users in a table
- View detailed information about a specific user
- Create new users
- Update existing users
- Delete users

## Technologies Used

- React
- TypeScript
- Vite
- React Router
- Tailwind CSS
- shadcn/ui components

## Getting Started

### Prerequisites

- Node.js (v14 or later)
- npm or yarn

### Installation

1. Clone the repository
2. Navigate to the project directory
3. Install dependencies:

```bash
npm install
# or
yarn
```

### Development

To start the development server:

```bash
npm run dev
# or
yarn dev
```

This will start the development server at http://localhost:5173.

### Building for Production

To build the application for production:

```bash
npm run build
# or
yarn build
```

The built files will be in the `dist` directory.

### Preview Production Build

To preview the production build:

```bash
npm run preview
# or
yarn preview
```

## API Configuration

The application is configured to proxy API requests to the backend server. By default, it assumes the backend is running at http://localhost:8080.

You can modify the API URL in the `vite.config.ts` file if your backend is running on a different port or host.

## Project Structure

- `src/components`: React components
- `src/services`: API service functions
- `src/types`: TypeScript type definitions
- `src/lib`: Utility functions