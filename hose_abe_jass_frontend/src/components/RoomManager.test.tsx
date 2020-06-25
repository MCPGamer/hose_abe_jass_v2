import React from 'react';
import {render} from '@testing-library/react';
import {RoomManager} from './RoomManager';

describe('RoomManager', () => {
    const renderRoomManager = (onJoin: (username: string, roomCode: string) => void, onCreate: (username: string) => void) => {
        return render(<RoomManager onCreate={onCreate} onJoin={onJoin} errorMsg={''}/>);
    }

    it('should render form',  () => {
        const onJoin = jest.fn();
        const onCreate = jest.fn();
        const result = renderRoomManager(onJoin, onCreate);
        expect(result.container.querySelector('form').tagName).toBe('FORM');
    });
})
