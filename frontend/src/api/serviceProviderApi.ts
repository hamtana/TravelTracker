import useFetch from "../useAuthenticatedFetch";

export interface ServiceProvider {
    id?: number;
  name: string;
  address: string;
}

const API_BASE = "http://localhost:8080/api/service-providers"

export function ServiceProviderApi(){
    const authenticatedFetch = useFetch();


    /**
     * Get all Service Providers
     * @returns ServiceProviders[]
     */
    const getServiceProviders = async (): Promise<ServiceProvider[]> => {
        return authenticatedFetch(API_BASE);
    };

/**
 * Add a new Service Provider
 * @param serviceProvider 
 * @returns 
 */
  const addServiceProvider = async (serviceProvider: ServiceProvider): Promise<ServiceProvider> => {
    return authenticatedFetch(API_BASE, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(serviceProvider),
    });
  };

  /**
   * Update a Service Provider
   * @param id 
   * @returns ServiceProvider
   */
  const getServiceProviderById = async (id: number): Promise<ServiceProvider> => {
    try{
       return authenticatedFetch(`${API_BASE}/${id}`);
    } catch (err: any) {
      if (err.message.includes("404")) {
        throw new Error("No Service Provider found with that NHI");
      }
      throw err;
    }
  };

  /**
   * Update Service Provider
   * @param id 
   * @param serviceProvider 
   * @returns 
   */
  const updateServiceProvider = async (id: number, serviceProvider : ServiceProvider): Promise<ServiceProvider> => {
      return authenticatedFetch(`${API_BASE}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(serviceProvider),
    });
  };

  /**
   * Delete a Service Provider
   */
  const deleteServiceProvider = async (id: number): Promise<void> => {
    await authenticatedFetch(`${API_BASE}/${id}`, {
      method: "DELETE",
    });
  };

  return{
    deleteServiceProvider,
    getServiceProviderById,
    getServiceProviders,
    updateServiceProvider,
    addServiceProvider,
  }



}