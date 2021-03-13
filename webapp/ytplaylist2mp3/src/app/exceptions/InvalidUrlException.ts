export class InvalidUrlException extends Error {
    constructor(message: string) {
        super(message)
        Object.setPrototypeOf(this, InvalidUrlException.prototype)
    }
}