import {Player} from './player';
import {Card} from './card';

export type Room = {
    roomCode: string,
    host: Player,
    players: Player[],
    table: Card[],
    finalRound: boolean,
    playerTurn: number,
    gameOver: boolean;
}

export const defaultRoom: Room = {
    roomCode: '',
    host: {
        cards: [],
        name: '',
        finalTurnPlayed: false
    },
    players: [],
    table: [],
    finalRound: false,
    playerTurn: 0,
    gameOver: false
};
