import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Toaster } from '@/components/ui/toaster';
import UserList from '@/components/UserList';
import UserDetail from '@/components/UserDetail';
import Header from '@/components/Header';

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        <Header />
        <main className="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
          <Routes>
            <Route path="/" element={<UserList />} />
            <Route path="/users/:userId" element={<UserDetail />} />
          </Routes>
        </main>
        <Toaster />
      </div>
    </Router>
  );
}

export default App;