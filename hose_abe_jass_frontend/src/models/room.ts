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
