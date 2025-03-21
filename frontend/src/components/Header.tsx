import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <header className="bg-white shadow">
      <div className="container mx-auto px-4 py-4 flex justify-between items-center">
        <Link to="/" className="text-xl font-bold text-primary">
          User Management System
        </Link>
        <nav>
          <Link 
            to="/" 
            className="text-gray-600 hover:text-primary transition-colors"
          >
            Users
          </Link>
        </nav>
      </div>
    </header>
  );
};

export default Header;