export interface TranscodeApi {
    codecsVideo: string[];
    codecsAudio: string[];
    presets: string[];
    resolutions: string[];
    videoContainers: string[];
    audioContainers?: string[];
}
