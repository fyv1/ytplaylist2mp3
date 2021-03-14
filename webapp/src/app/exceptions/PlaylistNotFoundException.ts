export class PlaylistNotFoundException extends Error {
    constructor(message: string) {
        super(message)
        Object.setPrototypeOf(this, PlaylistNotFoundException.prototype)
    }
}