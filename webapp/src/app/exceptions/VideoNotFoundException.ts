export class VideoNotFoundException extends Error {
    constructor(message: string) {
        super(message)
        Object.setPrototypeOf(this, VideoNotFoundException.prototype)
    }
}