import {Player} from './player';
import {Card} from './card';

export type Room = {
    roomCode: string,
    host: Player,
    players: Player[],
    originPlayerOrder: Player[],
    table: Card[],
    finalRound: boolean,
    playerTurn: number,
    gameOver: boolean,
    roundOver: boolean,
    bonusLifeUsed: boolean,
    roundsPlayed:number
}

export const defaultRoom: Room = {
    roomCode: '',
    host: {
        cards: [],
        name: '',
        finalTurnPlayed: false,
        life:3,
        hasBonusLife:false
    },
    players: [],
    originPlayerOrder: [],
    table: [],
    finalRound: false,
    playerTurn: 0,
    gameOver: false,
    roundOver: false,
    bonusLifeUsed:false,
    roundsPlayed:0
};
