const CountryDb = require('./country.db');

const CountryService = {};

CountryService.find = () => CountryDb.list().map((country) => country.name);

module.exports = CountryService;