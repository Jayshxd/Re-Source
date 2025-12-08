import { useState, useEffect } from 'react';
import { Card, CardHeader, CardContent } from '../../components/ui/Card';
import Button from '../../components/ui/Button';
import Input from '../../components/ui/Input';
import Modal from '../../components/ui/Modal';
import { useToast } from '../../components/ui/Toast';
import { trackService, assetService, employeeService } from '../../services';
import { Plus, CheckCircle, XCircle, Clock } from 'lucide-react';
import Spinner from '../../components/ui/Spinner';
import { formatDate, formatDateTime } from '../../lib/utils';

const Tracking = () => {
  const [tracks, setTracks] = useState([]);
  const [assets, setAssets] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isAssignModalOpen, setIsAssignModalOpen] = useState(false);
  const [isReturnModalOpen, setIsReturnModalOpen] = useState(false);
  const [selectedTrack, setSelectedTrack] = useState(null);
  const [assignFormData, setAssignFormData] = useState({
    employeeId: '',
    assetId: '',
    expectedReturnDate: '',
  });
  const [returnFormData, setReturnFormData] = useState({
    assetCondition: 'Good',
    returnDate: '',
    returnTime: '',
  });
  const toast = useToast();

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [tracksRes, assetsRes, employeesRes] = await Promise.all([
        trackService.search({}),
        assetService.getAll(),
        employeeService.getAll(),
      ]);
      setTracks(tracksRes.data);
      setAssets(assetsRes.data);
      setEmployees(employeesRes.data);
    } catch (error) {
      toast.error('Failed to load data');
    } finally {
      setLoading(false);
    }
  };

  const handleAssignAsset = async (e) => {
    e.preventDefault();
    try {
      await trackService.assignAsset(assignFormData);
      toast.success('Asset assigned successfully');
      setIsAssignModalOpen(false);
      setAssignFormData({ employeeId: '', assetId: '', expectedReturnDate: '' });
      fetchData();
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to assign asset');
    }
  };

  const handleReturnAsset = async (e) => {
    e.preventDefault();
    try {
      await trackService.returnAsset({
        trackId: selectedTrack.id,
        ...returnFormData,
      });
      toast.success('Asset returned successfully');
      setIsReturnModalOpen(false);
      setSelectedTrack(null);
      setReturnFormData({ assetCondition: 'Good', returnDate: '', returnTime: '' });
      fetchData();
    } catch (error) {
      toast.error('Failed to return asset');
    }
  };

  const openReturnModal = (track) => {
    setSelectedTrack(track);
    const now = new Date();
    setReturnFormData({
      assetCondition: 'Good',
      returnDate: now.toISOString().split('T')[0],
      returnTime: now.toTimeString().split(' ')[0],
    });
    setIsReturnModalOpen(true);
  };

  const getStatusBadge = (track) => {
    if (track.returned) {
      return (
        <span className="flex items-center gap-1 px-2 py-1 bg-green-500/20 text-green-400 rounded text-sm">
          <CheckCircle size={14} />
          Returned
        </span>
      );
    }
    
    const expectedDate = new Date(track.expectedReturnDate);
    expectedDate.setHours(0, 0, 0, 0);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    
    if (expectedDate < today) {
      return (
        <span className="flex items-center gap-1 px-2 py-1 bg-red-500/20 text-red-400 rounded text-sm">
          <XCircle size={14} />
          Overdue
        </span>
      );
    }
    
    return (
      <span className="flex items-center gap-1 px-2 py-1 bg-blue-500/20 text-blue-400 rounded text-sm">
        <Clock size={14} />
        Active
      </span>
    );
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-full">
        <Spinner size="lg" />
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-3xl font-bold text-slate-100">Asset Tracking</h2>
          <p className="text-slate-400 mt-1">Track asset assignments and returns</p>
        </div>
        <Button onClick={() => setIsAssignModalOpen(true)}>
          <Plus size={20} className="mr-2" />
          Assign Asset
        </Button>
      </div>

      <Card>
        <CardContent>
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="border-b border-dark-800">
                  <th className="text-left py-3 px-4 text-sm font-semibold text-slate-300">Asset</th>
                  <th className="text-left py-3 px-4 text-sm font-semibold text-slate-300">Employee</th>
                  <th className="text-left py-3 px-4 text-sm font-semibold text-slate-300">Issue Date</th>
                  <th className="text-left py-3 px-4 text-sm font-semibold text-slate-300">Expected Return</th>
                  <th className="text-left py-3 px-4 text-sm font-semibold text-slate-300">Status</th>
                  <th className="text-left py-3 px-4 text-sm font-semibold text-slate-300">Condition</th>
                  <th className="text-right py-3 px-4 text-sm font-semibold text-slate-300">Actions</th>
                </tr>
              </thead>
              <tbody>
                {tracks.length === 0 ? (
                  <tr>
                    <td colSpan="7" className="text-center py-8 text-slate-400">
                      No tracking records found
                    </td>
                  </tr>
                ) : (
                  tracks.map((track) => (
                    <tr key={track.id} className="border-b border-dark-800 hover:bg-dark-800/50 transition-colors">
                      <td className="py-3 px-4 text-slate-100">{track.asset?.name || 'N/A'}</td>
                      <td className="py-3 px-4 text-slate-300">{track.employee?.name || 'N/A'}</td>
                      <td className="py-3 px-4 text-slate-300">
                        {formatDateTime(track.issueDate, track.issueTime)}
                      </td>
                      <td className="py-3 px-4 text-slate-300">{formatDate(track.expectedReturnDate)}</td>
                      <td className="py-3 px-4">{getStatusBadge(track)}</td>
                      <td className="py-3 px-4 text-slate-300">{track.assetCondition || 'N/A'}</td>
                      <td className="py-3 px-4">
                        <div className="flex items-center justify-end">
                          {!track.returned && (
                            <Button
                              size="sm"
                              variant="secondary"
                              onClick={() => openReturnModal(track)}
                            >
                              Return
                            </Button>
                          )}
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

      {/* Assign Asset Modal */}
      <Modal isOpen={isAssignModalOpen} onClose={() => setIsAssignModalOpen(false)} title="Assign Asset">
        <form onSubmit={handleAssignAsset} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-slate-300 mb-1.5">Employee</label>
            <select
              className="w-full px-3 py-2 bg-dark-900 border border-dark-700 rounded-lg text-slate-100"
              value={assignFormData.employeeId}
              onChange={(e) => setAssignFormData({ ...assignFormData, employeeId: e.target.value })}
              required
            >
              <option value="">Select Employee</option>
              {employees.map((emp) => (
                <option key={emp.id} value={emp.id}>
                  {emp.name} ({emp.email})
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium text-slate-300 mb-1.5">Asset</label>
            <select
              className="w-full px-3 py-2 bg-dark-900 border border-dark-700 rounded-lg text-slate-100"
              value={assignFormData.assetId}
              onChange={(e) => setAssignFormData({ ...assignFormData, assetId: e.target.value })}
              required
            >
              <option value="">Select Asset</option>
              {assets.map((asset) => (
                <option key={asset.id} value={asset.id}>
                  {asset.name} - {asset.serialNumber}
                </option>
              ))}
            </select>
          </div>

          <Input
            label="Expected Return Date"
            type="date"
            value={assignFormData.expectedReturnDate}
            onChange={(e) => setAssignFormData({ ...assignFormData, expectedReturnDate: e.target.value })}
            required
          />

          <div className="flex gap-3 justify-end pt-4">
            <Button type="button" variant="secondary" onClick={() => setIsAssignModalOpen(false)}>
              Cancel
            </Button>
            <Button type="submit">Assign Asset</Button>
          </div>
        </form>
      </Modal>

      {/* Return Asset Modal */}
      <Modal isOpen={isReturnModalOpen} onClose={() => setIsReturnModalOpen(false)} title="Return Asset">
        <form onSubmit={handleReturnAsset} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-slate-300 mb-1.5">Asset Condition</label>
            <select
              className="w-full px-3 py-2 bg-dark-900 border border-dark-700 rounded-lg text-slate-100"
              value={returnFormData.assetCondition}
              onChange={(e) => setReturnFormData({ ...returnFormData, assetCondition: e.target.value })}
              required
            >
              <option value="Excellent">Excellent</option>
              <option value="Good">Good</option>
              <option value="Fair">Fair</option>
              <option value="Poor">Poor</option>
              <option value="Damaged">Damaged</option>
            </select>
          </div>

          <Input
            label="Return Date"
            type="date"
            value={returnFormData.returnDate}
            onChange={(e) => setReturnFormData({ ...returnFormData, returnDate: e.target.value })}
            required
          />

          <Input
            label="Return Time"
            type="time"
            value={returnFormData.returnTime}
            onChange={(e) => setReturnFormData({ ...returnFormData, returnTime: e.target.value })}
            required
          />

          <div className="flex gap-3 justify-end pt-4">
            <Button type="button" variant="secondary" onClick={() => setIsReturnModalOpen(false)}>
              Cancel
            </Button>
            <Button type="submit">Return Asset</Button>
          </div>
        </form>
      </Modal>
    </div>
  );
};

export default Tracking;
