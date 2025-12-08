import { useState, useEffect } from 'react';
import { Card, CardHeader, CardTitle, CardContent } from '../../components/ui/Card';
import { trackService } from '../../services';
import { useToast } from '../../components/ui/Toast';
import Spinner from '../../components/ui/Spinner';
import { Package, Users, TrendingUp, AlertTriangle } from 'lucide-react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, PieChart, Pie, Cell } from 'recharts';

const Dashboard = () => {
  const [analytics, setAnalytics] = useState(null);
  const [loading, setLoading] = useState(true);
  const toast = useToast();

  useEffect(() => {
    fetchAnalytics();
  }, []);

  const fetchAnalytics = async () => {
    try {
      const response = await trackService.getAnalytics();
      setAnalytics(response.data);
    } catch (error) {
      toast.error('Failed to load analytics');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-full">
        <Spinner size="lg" />
      </div>
    );
  }

  const stats = [
    {
      title: 'Total Assets',
      value: analytics?.totalTracks || 0,
      icon: Package,
      color: 'text-blue-500',
      bgColor: 'bg-blue-500/10',
    },
    {
      title: 'Active Assignments',
      value: analytics?.activeAssignments || 0,
      icon: TrendingUp,
      color: 'text-green-500',
      bgColor: 'bg-green-500/10',
    },
    {
      title: 'Returned Assets',
      value: analytics?.returnedAssets || 0,
      icon: Users,
      color: 'text-purple-500',
      bgColor: 'bg-purple-500/10',
    },
    {
      title: 'Overdue Assets',
      value: analytics?.overdueAssets || 0,
      icon: AlertTriangle,
      color: 'text-red-500',
      bgColor: 'bg-red-500/10',
    },
  ];

  const pieData = [
    { name: 'Active', value: analytics?.activeAssignments || 0 },
    { name: 'Returned', value: analytics?.returnedAssets || 0 },
    { name: 'Overdue', value: analytics?.overdueAssets || 0 },
  ];

  const COLORS = ['#10b981', '#8b5cf6', '#ef4444'];

  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-3xl font-bold text-slate-100">Dashboard</h2>
        <p className="text-slate-400 mt-1">Overview of your asset management system</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {stats.map((stat, index) => {
          const Icon = stat.icon;
          return (
            <Card key={index} className="hover:shadow-xl transition-shadow duration-300">
              <CardContent className="py-6">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm text-slate-400 mb-1">{stat.title}</p>
                    <p className="text-3xl font-bold text-slate-100">{stat.value}</p>
                  </div>
                  <div className={`${stat.bgColor} p-3 rounded-lg`}>
                    <Icon className={stat.color} size={24} />
                  </div>
                </div>
              </CardContent>
            </Card>
          );
        })}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card>
          <CardHeader>
            <CardTitle>Asset Distribution</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie
                  data={pieData}
                  cx="50%"
                  cy="50%"
                  labelLine={false}
                  label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
                  outerRadius={80}
                  fill="#8884d8"
                  dataKey="value"
                >
                  {pieData.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip />
              </PieChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Asset Status Overview</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={[
                { name: 'Total', value: analytics?.totalTracks || 0 },
                { name: 'Active', value: analytics?.activeAssignments || 0 },
                { name: 'Returned', value: analytics?.returnedAssets || 0 },
                { name: 'Overdue', value: analytics?.overdueAssets || 0 },
              ]}>
                <CartesianGrid strokeDasharray="3 3" stroke="#334155" />
                <XAxis dataKey="name" stroke="#94a3b8" />
                <YAxis stroke="#94a3b8" />
                <Tooltip 
                  contentStyle={{ 
                    backgroundColor: '#1e293b', 
                    border: '1px solid #334155',
                    borderRadius: '8px'
                  }} 
                />
                <Bar dataKey="value" fill="#3b82f6" radius={[8, 8, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Quick Actions</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <button className="p-4 bg-dark-800 hover:bg-dark-700 border border-dark-700 rounded-lg transition-colors text-left">
              <Package className="text-primary-500 mb-2" size={24} />
              <h3 className="text-slate-100 font-semibold mb-1">Add New Asset</h3>
              <p className="text-sm text-slate-400">Register a new asset in the system</p>
            </button>
            <button className="p-4 bg-dark-800 hover:bg-dark-700 border border-dark-700 rounded-lg transition-colors text-left">
              <Users className="text-green-500 mb-2" size={24} />
              <h3 className="text-slate-100 font-semibold mb-1">Assign Asset</h3>
              <p className="text-sm text-slate-400">Assign an asset to an employee</p>
            </button>
            <button className="p-4 bg-dark-800 hover:bg-dark-700 border border-dark-700 rounded-lg transition-colors text-left">
              <TrendingUp className="text-purple-500 mb-2" size={24} />
              <h3 className="text-slate-100 font-semibold mb-1">View Reports</h3>
              <p className="text-sm text-slate-400">Generate asset tracking reports</p>
            </button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default Dashboard;
