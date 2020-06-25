import React from 'react';
import {render, fireEvent} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {RoomManager} from './RoomManager';

describe('RoomManager', () => {
    const renderRoomManager = (onJoin: (username: string, roomCode: string) => void, onCreate: (username: string) => void) => {
        return render(<RoomManager onCreate={onCreate} onJoin={onJoin} errorMsg={''} initialUsername={'TestUser'} initialRoomCode={'TestRoomCode'}/>);
    };

    it('should render form',  () => {
        const onJoin = jest.fn();
        const onCreate = jest.fn();
        const result = renderRoomManager(onJoin, onCreate);
        expect(result.container.querySelector('form').tagName).toBe('FORM');
    });

    it('should require username',  () => {
        const onJoin = jest.fn();
        const onCreate = jest.fn();
        const result = renderRoomManager(onJoin, onCreate);
        expect(result.container.querySelector('input').required).toBe(true);
    });

    it('should call onCreate when Create button is pressed',  () => {
        const onJoin = jest.fn();
        const onCreate = jest.fn();
        const result = renderRoomManager(onJoin, onCreate);
        const createButton = result.queryByText('Erstellen').parentElement;
        fireEvent.click(createButton);
        expect(onCreate).toBeCalledTimes(1);
    });

    it('should call onJoin when Join  button is pressed',  () => {
        const onJoin = jest.fn();
        const onCreate = jest.fn();
        const result = renderRoomManager(onJoin, onCreate);
        const joinButton = result.queryByText('Beitreten').parentElement;
        fireEvent.click(joinButton);
        expect(onJoin).toBeCalledTimes(1);
    });
});
