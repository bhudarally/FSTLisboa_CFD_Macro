function Y_appr = prvalue(Y,Xmap)

Nco = size(Y.CoeffsPower,1);  
A = Y.CoeffsPower;

cur = repmat(Xmap(1,:),Nco,1);
C = cur.^A;  
Weig(1,:) = eval(Y.MultipC);

Y_appr = Weig*(Y.Coefficients);

end

