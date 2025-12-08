import { useState, useEffect } from 'react';
import { Card, CardHeader, CardContent } from '../../components/ui/Card';
import Input from '../../components/ui/Input';
import Button from '../../components/ui/Button';
import { useToast } from '../../components/ui/Toast';
import { employeeService } from '../../services';
import { Search, User, Package } from 'lucide-react';
import Spinner from '../../components/ui/Spinner';

const Employees = () => {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');
  const toast = useToast();

  useEffect(() => {
    fetchEmployees();
  }, []);

  const fetchEmployees = async () => {
    try {
      const response = await employeeService.getAll();
      
      // Fetch track counts for each employee
      const employeesWithCounts = await Promise.all(
        response.data.map(async (emp) => {
          try {
            const countResponse = await employeeService.countEmployeeTracks(emp.id);
            return { ...emp, trackCount: countResponse.data };
          } catch {
            return { ...emp, trackCount: 0 };
          }
        })
      );
      
      setEmployees(employeesWithCounts);
    } catch (error) {
      toast.error('Failed to load employees');
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    if (!searchQuery.trim()) {
      fetchEmployees();
      return;
    }
    
    try {
      const response = await employeeService.getAll({ name: searchQuery });
      setEmployees(response.data);
    } catch (error) {
      toast.error('Search failed');
    }
  };

  const filteredEmployees = employees.filter(emp =>
    emp.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
    emp.email.toLowerCase().includes(searchQuery.toLowerCase()) ||
    emp.username.toLowerCase().includes(searchQuery.toLowerCase())
  );

  if (loading) {
    return (
      <div className="flex items-center justify-center h-full">
        <Spinner size="lg" />
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-3xl font-bold text-slate-100">Employees</h2>
        <p className="text-slate-400 mt-1">Manage employee information and assignments</p>
      </div>

      <Card>
        <CardHeader>
          <div className="flex items-center gap-4">
            <div className="flex-1 flex items-center gap-2">
              <Input
                placeholder="Search by name, email, or username..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
              />
              <Button onClick={handleSearch} variant="secondary">
                <Search size={20} />
              </Button>
            </div>
          </div>
        </CardHeader>
        <CardContent>
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="border-b border-dark-800">
                  <th className="text-left py-3 px-4 text-sm font-semibold text-slate-300">Name</th>
                  <th className="text-left py-3 px-4 text-sm font-semibold text-slate-300">Username</th>
                  <th className="text-left py-3 px-4 text-sm font-semibold text-slate-300">Email</th>
                  <th className="text-center py-3 px-4 text-sm font-semibold text-slate-300">Assets Assigned</th>
                </tr>
              </thead>
              <tbody>
                {filteredEmployees.length === 0 ? (
                  <tr>
                    <td colSpan="4" className="text-center py-8 text-slate-400">
                      No employees found
                    </td>
                  </tr>
                ) : (
                  filteredEmployees.map((employee) => (
                    <tr key={employee.id} className="border-b border-dark-800 hover:bg-dark-800/50 transition-colors">
                      <td className="py-3 px-4">
                        <div className="flex items-center gap-3">
                          <div className="h-10 w-10 rounded-full bg-primary-600 flex items-center justify-center">
                            <User size={20} className="text-white" />
                          </div>
                          <span className="text-slate-100 font-medium">{employee.name}</span>
                        </div>
                      </td>
                      <td className="py-3 px-4 text-slate-300">{employee.username}</td>
                      <td className="py-3 px-4 text-slate-300">{employee.email}</td>
                      <td className="py-3 px-4">
                        <div className="flex items-center justify-center gap-2">
                          <Package size={16} className="text-primary-400" />
                          <span className="text-slate-100 font-semibold">{employee.trackCount || 0}</span>
                        </div>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </CardContent>
      </Card>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card>
          <CardContent className="py-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-slate-400 mb-1">Total Employees</p>
                <p className="text-3xl font-bold text-slate-100">{employees.length}</p>
              </div>
              <div className="bg-blue-500/10 p-3 rounded-lg">
                <User className="text-blue-500" size={24} />
              </div>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="py-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-slate-400 mb-1">Total Assets Assigned</p>
                <p className="text-3xl font-bold text-slate-100">
                  {employees.reduce((sum, emp) => sum + (emp.trackCount || 0), 0)}
                </p>
              </div>
              <div className="bg-green-500/10 p-3 rounded-lg">
                <Package className="text-green-500" size={24} />
              </div>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="py-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-slate-400 mb-1">Avg Assets/Employee</p>
                <p className="text-3xl font-bold text-slate-100">
                  {employees.length > 0
                    ? (employees.reduce((sum, emp) => sum + (emp.trackCount || 0), 0) / employees.length).toFixed(1)
                    : 0}
                </p>
              </div>
              <div className="bg-purple-500/10 p-3 rounded-lg">
                <Package className="text-purple-500" size={24} />
              </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default Employees;
