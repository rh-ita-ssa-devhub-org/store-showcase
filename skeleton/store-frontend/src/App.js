import React, { useEffect, useState } from 'react';
import './App.css';
import List from './components/List';
import withListLoading from './components/withListLoading';
import axios from 'axios';
import { Config } from './config';


function App() {
  const ListLoading = withListLoading(List);
  const [appState, setAppState] = useState({
    loading: false,
    repos: null,
    error: null,
  });

  useEffect(() => {
    setAppState({ loading: true, repos: null, error: null });
    axios.get(Config.apiUrl)
      .then((repos) => {
        const allRepos = repos.data;
        setAppState({ loading: false, repos: allRepos, error: null });
      })
      .catch((error) => {
        console.error('API connection error:', error);
        let errorMessage = 'Unable to connect to the server. Please try again later.';
        if (error.code === 'ERR_NETWORK') {
          errorMessage = 'Network error: Unable to reach the server. Please check your connection.';
        } else if (error.response) {
          errorMessage = `Server error: ${error.response.status} - ${error.response.statusText}`;
        }
        setAppState({ loading: false, repos: null, error: errorMessage });
      });
  }, []);

  return (
    <div className='App'>
      <header className='header'>
        <div className='logo-container'>
          <span className='shop-icon'>🛒</span>
          <h1>Quarkus Shop</h1>
        </div>
        <p className='header-tagline'>Powered by supersonic Java</p>
      </header>
      
      <div className='repo-container'>
        {appState.error ? (
          <div className='error-message'>
            <div className='error-icon'>🔌</div>
            <p>{appState.error}</p>
            <button onClick={() => window.location.reload()}>Try Again</button>
          </div>
        ) : (
          <ListLoading isLoading={appState.loading} repos={appState.repos} />
        )}
      </div>
      
      <footer className='footer'>
        <p className='footer-text'>
          Built with <span>Quarkus</span> ⚡
        </p>
      </footer>
    </div>
  );
}
export default App;
