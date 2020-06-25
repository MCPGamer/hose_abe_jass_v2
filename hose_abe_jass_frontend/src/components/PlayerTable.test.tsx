import React from 'react';
import {render} from '@testing-library/react';
import { PlayerTable } from './PlayerTable';
import {defaultRoom, Room} from '../models/room';

describe('PlayerTable', () => {
  const renderPlayerTable = (username: string) => {
    const room:Room = defaultRoom;
    defaultRoom.players = [
      {
        finalTurnPlayed: false,
        cards: [],
        name: username
      },
      {
        finalTurnPlayed: false,
        cards: [],
        name: 'User2'
      }
    ];

    return render(<PlayerTable player={username} room={room}/>);
  };

  it('should render a table', () => {
    const username:string = 'Test1User';
    const result = renderPlayerTable(username);
    expect(result.container.querySelector('table')?.tagName).toBe('TABLE');
  });

  it('should set the first user as the first user in the array', () => {
    const username:string = 'Test2User';
    const result = renderPlayerTable(username);
    expect(result.container.querySelector('table > tbody')?.firstChild?.childNodes.item(1).textContent).toContain(username);
  });

  it('should render your username with " (du)" appended', () => {
    const username:string = 'Test3User';
    const result = renderPlayerTable(username);
    const allTds = result.container.querySelectorAll('td');

    const searchText = username + ' (du)';
    let found;
    for (let i = 0; i < allTds.length; i++) {
      if (allTds[i].textContent == searchText) {
        found = allTds[i];
        break;
      }
    }

    expect(found).not.toBe(undefined);
  });

  it('should have 1 player bold since it is his turn', () => {
    const username:string = 'Test4User';
    const result = renderPlayerTable(username);
    expect(result.container.querySelectorAll('.bold').length).toBe(1);
  });
});
