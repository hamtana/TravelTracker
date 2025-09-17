import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

// Utility function to combine and merge Tailwind class names
export function cn(...inputs: ClassValue[]): string {
  return twMerge(clsx(...inputs));
}