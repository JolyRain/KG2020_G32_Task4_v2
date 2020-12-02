package rasterization.buffer;

import screen.ScreenPoint;

public interface ZValueResolver {

    int resolve(ScreenPoint point);
}
