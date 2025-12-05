import React from 'react';

function WithListLoading(Component) {
  return function WithLoadingComponent({ isLoading, ...props }) {
    if (!isLoading) return <Component {...props} />;
    return (
      <div className='loading-wrapper'>
        <div className='loading-spinner'></div>
        <p className='loading-text'>Fetching orders...</p>
      </div>
    );
  };
}
export default WithListLoading;
