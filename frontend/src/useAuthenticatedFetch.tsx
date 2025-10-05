import { useAuth } from "@clerk/clerk-react"; 

type FetchArgs = [input: RequestInfo, init?: RequestInit];

export default function useFetch() {
  const { getToken } = useAuth();

  const authenticatedFetch = async (...args: FetchArgs): Promise<any> => {
    const token = await getToken();
    console.log("Sending token: ", token);

    const [input, init] = args;

    const response = await fetch(input, {
      ...init,
      headers: {
        ...(init?.headers || {}),
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }


    return response.json();
  };

  return authenticatedFetch;
}
