import { Bell, LogOut, User } from 'lucide-react';
import { useAuth } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const Header = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <header className="h-16 bg-dark-900 border-b border-dark-800 px-6 flex items-center justify-between">
      <div className="flex items-center gap-4">
        <h1 className="text-xl font-semibold text-slate-100">Asset Management System</h1>
      </div>

      <div className="flex items-center gap-4">
        <button className="relative p-2 text-slate-400 hover:text-slate-100 hover:bg-dark-800 rounded-lg transition-colors">
          <Bell size={20} />
          <span className="absolute top-1 right-1 h-2 w-2 bg-red-500 rounded-full"></span>
        </button>

        <div className="flex items-center gap-3 pl-4 border-l border-dark-800">
          <div className="flex items-center gap-2">
            <div className="h-8 w-8 rounded-full bg-primary-600 flex items-center justify-center">
              <User size={16} className="text-white" />
            </div>
            <div className="text-sm">
              <p className="font-medium text-slate-100">{user?.name || 'User'}</p>
              <p className="text-xs text-slate-500">{user?.email || ''}</p>
            </div>
          </div>
          
          <button
            onClick={handleLogout}
            className="p-2 text-slate-400 hover:text-red-500 hover:bg-dark-800 rounded-lg transition-colors"
            title="Logout"
          >
            <LogOut size={18} />
          </button>
        </div>
      </div>
    </header>
  );
};

export default Header;
