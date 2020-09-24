export interface TranscodeMedia {
    name: string;
    type: string;
    filesize: string;
    id: string;
    flatMediaId: string;
    container: string;
    path: string;
    audioCodec: string;
    videoCodec: string;
    preset: string;
    resolution: string;
    processed: string;
    complete: boolean;
    user: string;
    bitrate: string;
    pathFFconcat: string;
    nameFFconcat: string;
    transcodes: string[];
}
