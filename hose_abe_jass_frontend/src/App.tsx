import React, {useEffect, useState} from 'react';

export type pingRequest = {
  ping:boolean
};

function App() {
  const [fetchedData, setFetchedData] = useState<pingRequest>();

  useEffect(() => {
    fetch("http://localhost:8080/", {method:'Get'})
        .then(result => result.json())
        .then(data => setFetchedData(data));
  }, []);

  return (
      <div className="App">
        {fetchedData === undefined ? 'pinging' : fetchedData.ping ? 'Ping Successfull' : 'Ping Unsuccessfull' }
      </div>
  );
}

export default App;
