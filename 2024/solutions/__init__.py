import os

__all__ = [f[:-3] for f in os.listdir("solutions") if "__" not in f]
