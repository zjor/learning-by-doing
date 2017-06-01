import React, { Component } from 'react';
import './App.css';

function fetchData() {
  return fetch('/data.json').then(res => res.json());
}

class App extends Component {

  constructor() {
    super();
    this.state = {
      data: null
    };

    fetchData().then(data => this.setState({data: data}));
  }

  render() {
    let list = this.state.data ? this.state.data.map(item => (
      <div className="list" key={item.id}>{item.name}</div>
    )) : (<div>No data</div>);

    return (
      <div className="App">
        {list}
      </div>
    );
  }
}

export default App;
