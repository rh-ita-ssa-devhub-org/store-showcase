import React from 'react';

const List = (props) => {
  const { repos } = props;
  
  if (!repos || repos.length === 0) {
    return (
      <div className='empty-state'>
        <div className='empty-icon'>📭</div>
        <p>No orders yet</p>
      </div>
    );
  }
  
  return (
    <div>
      <h2 className='list-head'>Orders</h2>
      <ul className='list'>
        {repos.map((repo) => (
          <li key={repo.id}>
            <p className='repo-text'>{repo.name}</p>
            <p className='repo-description'>{repo.description}</p>
            <p>Quantity: <strong>{repo.quantity}</strong> · Category: <strong>{repo.itemCategory}</strong></p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default List;
