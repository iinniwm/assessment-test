import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <header className="bg-white shadow">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16 items-center">
          <Link to="/" className="text-xl font-bold text-blue-600">
            User Management System
          </Link>
          <nav>
            <Link 
              to="/" 
              className="text-gray-600 hover:text-blue-600 transition-colors"
            >
              Users
            </Link>
          </nav>
        </div>
      </div>
    </header>
  );
};

export default Header;