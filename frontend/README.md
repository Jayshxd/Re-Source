# Re:Source Frontend

A modern, enterprise-grade React frontend for the Re:Source Asset Management System.

## 🎨 Features

- **Modern UI**: Built with React 18, Vite, and Tailwind CSS
- **Dark Theme**: Professional dark mode with smooth animations
- **Responsive Design**: Works seamlessly on all screen sizes
- **Fast Performance**: Lightning-fast builds with Vite
- **JWT Authentication**: Secure authentication with the backend API

## 🚀 Tech Stack

- **React 18+** - Modern React with hooks
- **Vite** - Next-generation frontend tooling
- **Tailwind CSS** - Utility-first CSS framework
- **React Router** - Client-side routing
- **Axios** - HTTP client for API calls
- **Recharts** - Chart library for data visualization
- **Lucide React** - Beautiful icon library

## 📦 Installation

1. **Navigate to the frontend directory**
   ```bash
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure environment variables**
   
   Copy `.env.example` to `.env` and update the API base URL:
   ```
   VITE_API_BASE_URL=http://localhost:8080
   ```

4. **Start the development server**
   ```bash
   npm run dev
   ```

   The application will be available at `http://localhost:5173`

## 🏗️ Project Structure

```
frontend/
├── src/
│   ├── components/          # Reusable components
│   │   ├── layout/         # Layout components (Sidebar, Header)
│   │   └── ui/             # UI components (Button, Input, Modal, etc.)
│   ├── pages/              # Page components
│   │   ├── auth/           # Authentication pages
│   │   ├── dashboard/      # Dashboard page
│   │   ├── assets/         # Assets management page
│   │   ├── employees/      # Employees page
│   │   └── tracking/       # Asset tracking page
│   ├── services/           # API service layer
│   ├── context/            # React context (Auth)
│   ├── lib/                # Utility functions
│   ├── App.jsx             # Main app component
│   └── main.jsx            # Entry point
├── public/                 # Static assets
├── .env                    # Environment variables
├── .env.example            # Example environment file
├── index.html              # HTML template
├── package.json            # Dependencies
├── tailwind.config.js      # Tailwind configuration
├── vite.config.js          # Vite configuration
└── README.md              # This file
```

## 📱 Pages

### Authentication
- **Login** - Sign in with username and password
- **Register** - Create a new employee account

### Dashboard
- Analytics cards showing key metrics
- Charts for asset distribution and status
- Quick action buttons

### Assets Management
- View all assets in a data table
- Search and filter assets
- Create, edit, and delete assets
- View asset assignment history

### Employees
- View all employees
- Search employees by name, email, or username
- View assigned assets count for each employee
- Employee statistics

### Asset Tracking
- List all asset assignments
- Assign assets to employees
- Return assets with condition tracking
- Filter by status (active, returned, overdue)
- Visual indicators for overdue items

## 🔧 Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build locally
- `npm run lint` - Run ESLint

## 🎨 Customization

### Colors

The color scheme can be customized in `tailwind.config.js`:

```javascript
colors: {
  dark: { /* dark theme colors */ },
  primary: { /* primary brand colors */ },
}
```

### API Base URL

Update the API base URL in `.env`:

```
VITE_API_BASE_URL=http://your-api-url:port
```

## 🔐 Authentication

The application uses JWT tokens for authentication:

1. Login with credentials
2. Token stored in localStorage
3. Automatically included in API requests
4. Auto-logout on token expiration

## 🚀 Deployment

### Build for Production

```bash
npm run build
```

This creates an optimized production build in the `dist/` directory.

### Deploy to Static Hosting

The built files can be deployed to any static hosting service:

- **Vercel**: `vercel deploy`
- **Netlify**: Drag and drop `dist/` folder
- **GitHub Pages**: Use `gh-pages` branch
- **AWS S3**: Upload `dist/` contents

### Environment Variables for Production

Create a `.env.production` file:

```
VITE_API_BASE_URL=https://your-production-api.com
```

## 🌐 API Integration

The frontend connects to these backend endpoints:

### Auth
- `POST /auth/register` - Register new user
- `POST /auth/login` - Login

### Assets
- `GET /assets` - Get all assets
- `POST /assets` - Create asset
- `PUT /assets/{id}` - Update asset
- `DELETE /assets/{id}` - Delete asset
- `GET /assets/search` - Search assets

### Employees
- `GET /emps/all` - Get all employees
- `GET /emps/{empId}/tracks` - Get employee tracks
- `GET /emps/count/{empId}/tracks` - Count tracks

### Tracking
- `POST /tracks/assignAsset` - Assign asset
- `POST /tracks/returnAsset` - Return asset
- `GET /tracks/search` - Search tracks
- `GET /tracks/analytics` - Get analytics

## 🐛 Troubleshooting

### CORS Issues

If you encounter CORS errors, ensure the backend has CORS enabled for your frontend URL.

### API Connection Failed

1. Verify backend is running
2. Check `VITE_API_BASE_URL` in `.env`
3. Ensure no firewall blocking

### Build Errors

```bash
# Clear node_modules and reinstall
rm -rf node_modules package-lock.json
npm install
```

## 📄 License

This project is part of Re:Source Asset Management System.

## 🙏 Acknowledgments

- Built with ❤️ using React and Vite
- UI components inspired by modern design systems
- Icons by Lucide

---

**Made with 💙 for efficient asset management**
