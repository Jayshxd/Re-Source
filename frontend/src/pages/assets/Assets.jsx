import { useState, useEffect } from 'react';
import { Card, CardHeader, CardTitle, CardContent } from '../../components/ui/Card';
import Button from '../../components/ui/Button';
import Input from '../../components/ui/Input';
import Modal from '../../components/ui/Modal';
import { useToast } from '../../components/ui/Toast';
import { assetService } from '../../services';
import { Plus, Search, Edit, Trash2 } from 'lucide-react';
import Spinner from '../../components/ui/Spinner';

const Assets = () => {
  const [assets, setAssets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [selectedAsset, setSelectedAsset] = useState(null);
  const [formData, setFormData] = useState({ name: '', serialNumber: '', assetType: '' });
  const toast = useToast();

  useEffect(() => {
    fetchAssets();
  }, []);

  const fetchAssets = async () => {
    try {
      const response = await assetService.getAll();
      setAssets(response.data);
    } catch (error) {
      toast.error('Failed to load assets');
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    if (!searchQuery.trim()) {
      fetchAssets();
      return;
    }
    
    try {
      const response = await assetService.search({ name: searchQuery });
      setAssets(response.data);
    } catch (error) {
      toast.error('Search failed');
    }
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    try {
      await assetService.create(formData);
      toast.success('Asset created successfully');
      setIsCreateModalOpen(false);
      setFormData({ name: '', serialNumber: '', assetType: '' });
      fetchAssets();
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to create asset');
    }
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await assetService.update(selectedAsset.id, formData);
      toast.success('Asset updated successfully');
      setIsEditModalOpen(false);
      setSelectedAsset(null);
      setFormData({ name: '', serialNumber: '', assetType: '' });
      fetchAssets();
    } catch (error) {
      toast.error('Failed to update asset');
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this asset?')) return;
    
    try {
      await assetService.delete(id);
      toast.success('Asset deleted successfully');
      fetchAssets();
    } catch (error) {
      toast.error('Failed to delete asset');
    }
  };

  const openEditModal = (asset) => {
    setSelectedAsset(asset);
    setFormData({
      name: asset.name,
      serialNumber: asset.serialNumber,
      assetType: asset.assetType,
    });
    setIsEditModalOpen(true);
  };

  const filteredAssets = assets.filter(asset =>
    asset.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
    asset.serialNumber.toLowerCase().includes(searchQuery.toLowerCase()) ||
    asset.assetType.toLowerCase().includes(searchQuery.toLowerCase())
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
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-3xl font-bold text-slate-100">Assets</h2>
          <p className="text-slate-400 mt-1">Manage your organization's assets</p>
        </div>
        <Button onClick={() => setIsCreateModalOpen(true)}>
          <Plus size={20} className="mr-2" />
          Add Asset
        </Button>
      </div>

      <Card>
        <CardHeader>
          <div className="flex items-center gap-4">
            <div className="flex-1 flex items-center gap-2">
              <Input
                placeholder="Search by name, serial number, or type..."
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
                  <th className="text-left py-3 px-4 text-sm font-semibold text-slate-300">Serial Number</th>
                  <th className="text-left py-3 px-4 text-sm font-semibold text-slate-300">Asset Type</th>
                  <th className="text-right py-3 px-4 text-sm font-semibold text-slate-300">Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredAssets.length === 0 ? (
                  <tr>
                    <td colSpan="4" className="text-center py-8 text-slate-400">
                      No assets found
                    </td>
                  </tr>
                ) : (
                  filteredAssets.map((asset) => (
                    <tr key={asset.id} className="border-b border-dark-800 hover:bg-dark-800/50 transition-colors">
                      <td className="py-3 px-4 text-slate-100">{asset.name}</td>
                      <td className="py-3 px-4 text-slate-300">{asset.serialNumber}</td>
                      <td className="py-3 px-4">
                        <span className="px-2 py-1 bg-primary-500/20 text-primary-400 rounded text-sm">
                          {asset.assetType}
                        </span>
                      </td>
                      <td className="py-3 px-4">
                        <div className="flex items-center justify-end gap-2">
                          <button
                            onClick={() => openEditModal(asset)}
                            className="p-2 text-blue-400 hover:bg-blue-500/10 rounded transition-colors"
                            title="Edit"
                          >
                            <Edit size={18} />
                          </button>
                          <button
                            onClick={() => handleDelete(asset.id)}
                            className="p-2 text-red-400 hover:bg-red-500/10 rounded transition-colors"
                            title="Delete"
                          >
                            <Trash2 size={18} />
                          </button>
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

      {/* Create Modal */}
      <Modal isOpen={isCreateModalOpen} onClose={() => setIsCreateModalOpen(false)} title="Add New Asset">
        <form onSubmit={handleCreate} className="space-y-4">
          <Input
            label="Asset Name"
            placeholder="Enter asset name"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            required
          />
          <Input
            label="Serial Number"
            placeholder="Enter serial number"
            value={formData.serialNumber}
            onChange={(e) => setFormData({ ...formData, serialNumber: e.target.value })}
            required
          />
          <Input
            label="Asset Type"
            placeholder="Enter asset type"
            value={formData.assetType}
            onChange={(e) => setFormData({ ...formData, assetType: e.target.value })}
            required
          />
          <div className="flex gap-3 justify-end pt-4">
            <Button type="button" variant="secondary" onClick={() => setIsCreateModalOpen(false)}>
              Cancel
            </Button>
            <Button type="submit">Create Asset</Button>
          </div>
        </form>
      </Modal>

      {/* Edit Modal */}
      <Modal isOpen={isEditModalOpen} onClose={() => setIsEditModalOpen(false)} title="Edit Asset">
        <form onSubmit={handleUpdate} className="space-y-4">
          <Input
            label="Asset Name"
            placeholder="Enter asset name"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            required
          />
          <Input
            label="Serial Number"
            placeholder="Enter serial number"
            value={formData.serialNumber}
            onChange={(e) => setFormData({ ...formData, serialNumber: e.target.value })}
            required
          />
          <Input
            label="Asset Type"
            placeholder="Enter asset type"
            value={formData.assetType}
            onChange={(e) => setFormData({ ...formData, assetType: e.target.value })}
            required
          />
          <div className="flex gap-3 justify-end pt-4">
            <Button type="button" variant="secondary" onClick={() => setIsEditModalOpen(false)}>
              Cancel
            </Button>
            <Button type="submit">Update Asset</Button>
          </div>
        </form>
      </Modal>
    </div>
  );
};

export default Assets;
