import {Player} from './player';
import {Card} from './card';

export type Room = {
    roomCode: string,
    host: Player;
    players: Player[];
    table: Card[];
    finalRound: boolean
    playerturn: number;
}

export const defaultRoom: Room = {
    roomCode: '',
    host: {
        cards: [],
        name: ''
    },
    players: [],
    table: [],
    finalRound: false,
    playerturn: 0
};
