import React from 'react';
import {fireEvent, render} from '@testing-library/react';
import {RoomManager} from './RoomManager';

describe('RoomManager', () => {
    const renderRoomManager = ( onJoin: (username: string, roomCode: string) => void, onCreate: (username: string) => void) => {
        return render(<RoomManager onCreate={onCreate} onJoin={onJoin} errorMsg={''} />);
    };

    it('should render form',  () => {
        const onJoin = jest.fn();
        const onCreate = jest.fn();
        const result = renderRoomManager(onJoin, onCreate);
        expect(result.container.querySelector('form')?.tagName).toBe('FORM');
    });

    it('should require username',  () => {
        const onJoin = jest.fn();
        const onCreate = jest.fn();
        const result = renderRoomManager(onJoin, onCreate);
        expect(result.container.querySelector('input')?.required).toBe(true);
    });

    it('shouldnt call onCreate when Create button is pressed due to no Username',  () => {
        const onJoin = jest.fn();
        const onCreate = jest.fn();
        const result = renderRoomManager(onJoin, onCreate);
        const createButton = result.queryByText('Erstellen')?.parentElement;
        fireEvent.click(createButton);
        expect(onCreate).not.toBeCalledTimes(1);
    });

    it('shouldnt call onJoin when Join button is pressed due to no Username & RoomCode',  () => {
        const onJoin = jest.fn();
        const onCreate = jest.fn();
        const result = renderRoomManager(onJoin, onCreate);
        const joinButton = result.queryByText('Beitreten')?.parentElement;
        fireEvent.click(joinButton);
        expect(onJoin).not.toBeCalledTimes(1);
    });
});
