const GameDb = require('./game.db');

const GameService = {};

GameService.find = () =>
    GameDb
        .list()
        .map(game => [game.rom, game.name])
        .then((games) => {
            games.unshift(['', 'Any game']);
            return games;
        });

module.exports = GameService;