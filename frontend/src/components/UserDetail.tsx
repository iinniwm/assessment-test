import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { User } from '@/types/user';
import { fetchUserById } from '@/services/api';
import { Button } from '@/components/ui/button';
import { useToast } from '@/components/ui/use-toast';
import { ArrowLeft, MapPin, Phone, Globe, Briefcase } from 'lucide-react';

const UserDetail = () => {
  const { userId } = useParams<{ userId: string }>();
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();
  const { toast } = useToast();

  useEffect(() => {
    const loadUser = async () => {
      if (!userId) return;
      
      try {
        setLoading(true);
        const data = await fetchUserById(parseInt(userId));
        setUser(data);
        setError(null);
      } catch (err) {
        setError('Failed to load user details. Please try again later.');
        toast({
          title: 'Error',
          description: 'Failed to load user details',
          variant: 'destructive',
        });
      } finally {
        setLoading(false);
      }
    };

    loadUser();
  }, [userId, toast]);

  if (loading) {
    return (
      <div className="text-center py-10">
        <div className="inline-block h-8 w-8 animate-spin rounded-full border-4 border-primary border-r-transparent"></div>
        <p className="mt-2 text-gray-500">Loading user details...</p>
      </div>
    );
  }

  if (error || !user) {
    return (
      <div className="space-y-4">
        <Button variant="outline" onClick={() => navigate(-1)}>
          <ArrowLeft className="mr-2 h-4 w-4" /> Back
        </Button>
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded">
          {error || 'User not found'}
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <Button variant="outline" onClick={() => navigate(-1)}>
        <ArrowLeft className="mr-2 h-4 w-4" /> Back to Users
      </Button>
      
      <div className="bg-white shadow rounded-lg overflow-hidden">
        <div className="px-6 py-5 border-b border-gray-200">
          <h2 className="text-2xl font-bold text-gray-800">{user.name}</h2>
          <p className="text-gray-500">@{user.username}</p>
        </div>
        
        <div className="p-6 space-y-6">
          <div className="grid md:grid-cols-2 gap-6">
            <div className="space-y-4">
              <h3 className="text-lg font-medium">Contact Information</h3>
              
              <div className="space-y-2">
                <div className="flex items-center text-gray-600">
                  <svg className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                  </svg>
                  <span>{user.email}</span>
                </div>
                
                {user.phone && (
                  <div className="flex items-center text-gray-600">
                    <Phone className="h-5 w-5 mr-2" />
                    <span>{user.phone}</span>
                  </div>
                )}
                
                {user.website && (
                  <div className="flex items-center text-gray-600">
                    <Globe className="h-5 w-5 mr-2" />
                    <span>{user.website}</span>
                  </div>
                )}
              </div>
            </div>
            
            {user.address && (
              <div className="space-y-4">
                <h3 className="text-lg font-medium">Address</h3>
                
                <div className="space-y-2">
                  <div className="flex items-start text-gray-600">
                    <MapPin className="h-5 w-5 mr-2 mt-0.5" />
                    <div>
                      <p>{user.address.street}, {user.address.suite}</p>
                      <p>{user.address.city}, {user.address.zipcode}</p>
                      {user.address.geo && (
                        <p className="text-xs text-gray-500 mt-1">
                          Lat: {user.address.geo.lat}, Lng: {user.address.geo.lng}
                        </p>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            )}
          </div>
          
          {user.company && (
            <div className="pt-4 border-t border-gray-200">
              <h3 className="text-lg font-medium mb-4">Company</h3>
              
              <div className="space-y-2">
                <div className="flex items-start text-gray-600">
                  <Briefcase className="h-5 w-5 mr-2 mt-0.5" />
                  <div>
                    <p className="font-medium">{user.company.name}</p>
                    <p className="italic text-gray-500">"{user.company.catchPhrase}"</p>
                    <p className="text-sm text-gray-500">{user.company.bs}</p>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default UserDetail;